package com.mju.insuranceCompany.service.accident.service;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.global.utility.S3Client;
import com.mju.insuranceCompany.service.accident.controller.dto.*;
import com.mju.insuranceCompany.service.accident.domain.*;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccidentDocumentFile;
import com.mju.insuranceCompany.service.accident.exception.*;
import com.mju.insuranceCompany.service.accident.repository.AccidentRepository;
import com.mju.insuranceCompany.service.contract.domain.CarContract;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.insuranceCompany.service.customer.domain.Customer;
import com.mju.insuranceCompany.service.customer.exception.CustomerNotFoundException;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import com.mju.insuranceCompany.service.employee.domain.Employee;
import com.mju.insuranceCompany.service.employee.exception.EmployeeIdNotFoundException;
import com.mju.insuranceCompany.service.employee.repository.EmployeeRepository;
import com.mju.insuranceCompany.service.employee.service.AssignEmployeeService;
import com.mju.outerSystem.RequestOnSiteSystem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccidentService {

    private final AccidentRepository accidentRepository;
    private final ContractRepository contractRepository;
    private final S3Client s3Client;
    private final AssignEmployeeService assignEmployeeService;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    /**
     * 자동차 관련 사고 접수를 요청한 고객을 검증하는 메소드.
     * Throw 2 cases:
     * 1. 요청 고객이 자동차 보험에 가입되어 있지 않은 경우
     * 2. 요청 고객이 가입한 자동차보험 중 사고 접수 요청한 자동차보험이 없는 경우
     */
    private void validateCustomerCanReportOnCar(AccidentReportDto accidentReportDto, int customerId) {
        List<CarContract> contractList = contractRepository.findCarContractByCustomerId(customerId)
                .filter(c -> {
                    if (c.isEmpty()) throw new CannotReportCarAccidentException(); // 요청 고객이 자동차보험에 가입되어 있지 않은 경우 예외 발생
                    return true;
                }).orElseThrow(ContractNotFoundRequestClientException::new);
        String requestedCarNo = accidentReportDto.getCarNo();
        contractList.stream()
                .filter(c -> c.getCarNo().equals(requestedCarNo))
                .findAny().orElseThrow(NotExistRequestedCarNoException::new); // 요청 고객이 가입한 자동차보험 중 사고 접수 요청한 자동차보험이 없는 경우 예외 발생
    }

    /** 자동차 사고 접수 */
    public CarAccidentDto reportCarAccident(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        validateCustomerCanReportOnCar(accidentReportDto, customerId);

        Accident accident = Accident.createAccident(AccidentType.CAR_ACCIDENT, customerId, accidentReportDto);
        accidentRepository.save(accident);
        AccidentWorkerDto accidentWorkerDto = null;
        if(accident.isRequestOnSite()) {
            accidentWorkerDto = AccidentWorkerDto.toDto(RequestOnSiteSystem.connectWorker());
            if(accidentWorkerDto == null) { // TODO 시연을 위한 예외상황을 만들어줘야 하나?
                throw new OnSiteSystemResponseErrorException();
            }
        }
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return CarAccidentDto.toDto((CarAccident) accident, accidentWorkerDto, fileList);
    }

    /** 자동차 고장 접수 */
    public CarBreakdownDto reportCarBreakdown(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        validateCustomerCanReportOnCar(accidentReportDto, customerId);

        Accident accident = Accident.createAccident(AccidentType.CAR_BREAKDOWN, customerId, accidentReportDto);
        accidentRepository.save(accident);
        AccidentWorkerDto accidentWorkerDto = AccidentWorkerDto.toDto(RequestOnSiteSystem.connectWorker());
        if(accidentWorkerDto == null) { // TODO 시연을 위한 예외상황을 만들어줘야 하나?
            throw new OnSiteSystemResponseErrorException();
        }
        return CarBreakdownDto.toDto((CarBreakdown) accident, accidentWorkerDto);
    }

    /** 화재 사고 접수 */
    public FireAccidentDto reportFireAccident(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        contractRepository.findFireContractByCustomerId(customerId)
                .filter(f -> {
                    if(f.isEmpty()) throw new CannotReportFireAccidentException();
                    return true;
                }).orElseThrow(ContractNotFoundRequestClientException::new);

        Accident accident = Accident.createAccident(AccidentType.FIRE_ACCIDENT, customerId, accidentReportDto);
        accidentRepository.save(accident);
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return FireAccidentDto.toDto((FireAccident) accident, fileList);
    }

    /** 상해 사고 접수 */
    public InjuryAccidentDto reportInjuryAccident(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        contractRepository.findHealthContractByCustomerId(customerId)
                .filter(h -> {
                    if(h.isEmpty()) throw new CannotReportInjuryAccidentException();
                    return true;
                }).orElseThrow(ContractNotFoundRequestClientException::new);

        Accident accident = Accident.createAccident(AccidentType.INJURY_ACCIDENT, customerId, accidentReportDto);
        accidentRepository.save(accident);
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return InjuryAccidentDto.toDto((InjuryAccident) accident, fileList);
    }

    /**
     * 보상금 청구를 위해 자동차/화재/상해 사고에 대한 타입 검증(자동차 고장은 보상금 청구를 할 수 없음)과
     * 해당 사고가 고객의 사고가 맞는지에 대한 검증을 수행하는 메소드.
     */
    private Accident validateClientAndAccidentType(int accidentId, AccidentType accidentType) {
        if(accidentType == AccidentType.CAR_BREAKDOWN) {
            throw new CannotClaimCarBreakdownException();
        }
        // Read Accident
        Accident accident = accidentRepository.findById(accidentId)
                .orElseThrow(AccidentIdNotFoundException::new);
        validateClient(accident);
        if(accident.getAccidentType() != accidentType) {
            throw new MismatchAccidentTypeException();
        }
        return accident;
    }

    /** 선택한 사고의 고객 ID와 요청 고객 ID를 검증하는 메소드. */
    private void validateClient(Accident accident) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        if(accident.getCustomerId() != customerId) {
            throw new MismatchRequestClientAndAccidentException();
        }
    }

    /**
     * 사고 관련 파일을 S3에 업로드하는 메소드.
     * @param accidentId 업로드하는 파일의 사고 ID
     * @param docType 업로드하는 파일의 타입
     * @param multipartFile 업로드하는 파일
     * @param accidentType 업로드하는 파일의 사고의 타입
     */
    public void submitAccidentDocumentFile(int accidentId, AccDocType docType,
                                           MultipartFile multipartFile, AccidentType accidentType) {
        Accident accident = validateClientAndAccidentType(accidentId, accidentType);
        String fileUrl = s3Client.uploadFile("acc_doc", multipartFile); // 파일 저장
        accident.addAccidentDocumentFile(docType, fileUrl); // accident's accident document file 추가
        accidentRepository.save(accident);
    }

    /** 보상금 청구 */
    public CompEmployeeDto claimCompensation(int accidentId) {
        Accident accident = accidentRepository.findById(accidentId)
                .orElseThrow(AccidentIdNotFoundException::new);
        if(!accident.checkConditionForClaimCompensation(accident.getAccidentType())) {
            throw new InsufficientSubmitAccDocFileException(); // 보상금 청구에 만족하는 파일이 모두 저장되어 있지 않는 경우
        }

        Employee employee = assignEmployeeService.assignCompEmployee();
        accident.assignEmployeeId(employee.getId());
        accidentRepository.save(accident);
        return CompEmployeeDto.toDto(employee);
    }

    /** 로그인 고객의 사고 접수 리스트 조회 */
    public List<AccidentListInfoDto> getAccidentListOfCustomer() {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        List<Accident> accidentList = accidentRepository.findAllByCustomerId(customerId);
        if(accidentList.isEmpty()) throw new NotExistClientAccidentsException(); // 고객이 접수한 사고가 없을 경우

        List<AccidentListInfoDto> accidentListInfoDtoList = new ArrayList<>();
        for(Accident accident : accidentList) {
            CompEmployeeDto compEmployee = null;
            if(accident.getEmployeeId() != 0) {
                Employee employee = employeeRepository.findById(accident.getEmployeeId())
                        .orElseThrow(EmployeeIdNotFoundException::new);
                compEmployee = CompEmployeeDto.toDto(employee);
            }
            accidentListInfoDtoList.add(AccidentListInfoDto.toDto(accident, compEmployee));
        }
        return accidentListInfoDtoList;
    }

    /** 자동차 사고 정보 조회 */
    public CarAccidentDto getCarAccident(int accidentId) {
        Accident accident = accidentRepository.findById(accidentId).orElseThrow(AccidentIdNotFoundException::new);
        validateClient(accident);
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return CarAccidentDto.toDto((CarAccident) accident, null, fileList);
    }

    /** 자동차 고장 정보 조회 */
    public CarBreakdownDto getCarBreakdown(int accidentId) {
        Accident accident = accidentRepository.findById(accidentId).orElseThrow(AccidentIdNotFoundException::new);
        validateClient(accident);
        return CarBreakdownDto.toDto((CarBreakdown) accident, null);
    }

    /** 화재 사고 정보 조회 */
    public FireAccidentDto getFireAccident(int accidentId) {
        Accident accident = accidentRepository.findById(accidentId).orElseThrow(AccidentIdNotFoundException::new);
        validateClient(accident);
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return FireAccidentDto.toDto((FireAccident) accident, fileList);
    }

    /** 상해 사고 정보 조회 */
    public InjuryAccidentDto getInjuryAccident(int accidentId) {
        Accident accident = accidentRepository.findById(accidentId).orElseThrow(AccidentIdNotFoundException::new);
        validateClient(accident);
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return InjuryAccidentDto.toDto((InjuryAccident) accident, fileList);
    }

    /** 보상처리담당 직원 변경 */
    public CompEmployeeDto changeCompEmployee(int accidentId, ComplainRequestDto dto) {
        Accident accident = accidentRepository.findById(accidentId).orElseThrow(AccidentIdNotFoundException::new);
        validateClient(accident);
        if(accident.getEmployeeId() == 0) { // validate of 보험직원을 변경할 사고에 보상담당직원이 배정되어 있지 않은 경우
            throw new NotYetAssignedCompEmployeeException();
        }
        // 1. Complain 등록
        Customer customer = customerRepository.findById(accident.getCustomerId())
                .orElseThrow(CustomerNotFoundException::new);
        customer.addComplain(dto);
        customerRepository.save(customer);

        // 2. 보상담당자 배정
        Employee employee = assignEmployeeService.changeCompEmployee(accident.getEmployeeId());
        accident.assignEmployeeId(employee.getId());
        accidentRepository.save(accident);
        return CompEmployeeDto.toDto(employee);
    }
}
