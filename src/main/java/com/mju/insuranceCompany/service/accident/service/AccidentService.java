package com.mju.insuranceCompany.service.accident.service;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.global.utility.S3Client;
import com.mju.insuranceCompany.service.accident.controller.dto.*;
import com.mju.insuranceCompany.service.accident.domain.*;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.service.accident.exception.*;
import com.mju.insuranceCompany.service.accident.repository.AccidentRepository;
import com.mju.insuranceCompany.service.contract.domain.CarContract;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
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
        return CarAccidentDto.toDto((CarAccident) accident, accidentWorkerDto);
    }

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

    public FireAccidentDto reportFireAccident(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        contractRepository.findFireContractByCustomerId(customerId)
                .filter(f -> {
                    if(f.isEmpty()) throw new CannotReportFireAccidentException();
                    return true;
                }).orElseThrow(ContractNotFoundRequestClientException::new);

        Accident accident = Accident.createAccident(AccidentType.FIRE_ACCIDENT, customerId, accidentReportDto);
        accidentRepository.save(accident);
        return FireAccidentDto.toDto((FireAccident) accident);
    }

    public InjuryAccidentDto reportInjuryAccident(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        contractRepository.findHealthContractByCustomerId(customerId)
                .filter(h -> {
                    if(h.isEmpty()) throw new CannotReportInjuryAccidentException();
                    return true;
                }).orElseThrow(ContractNotFoundRequestClientException::new);

        Accident accident = Accident.createAccident(AccidentType.INJURY_ACCIDENT, customerId, accidentReportDto);
        accidentRepository.save(accident);
        return InjuryAccidentDto.toDto((InjuryAccident) accident);
    }

    private Accident validateClientAndAccidentType(int accidentId, AccidentType accidentType) {
        if(accidentType == AccidentType.CAR_BREAKDOWN) {
            throw new CannotClaimCarBreakdownException();
        }
        // Read Accident
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        Accident accident = accidentRepository.findById(accidentId)
                .orElseThrow(AccidentIdNotFoundException::new);
        if(accident.getCustomerId() != customerId) {
            throw new MismatchRequestClientAndAccidentException();
        }
        if(accident.getAccidentType() != accidentType) {
            throw new MismatchAccidentTypeException();
        }
        return accident;
    }

    public void submitAccidentDocumentFile(int accidentId, AccDocType docType,
                                           MultipartFile multipartFile, AccidentType accidentType) {
        Accident accident = validateClientAndAccidentType(accidentId, accidentType);
        String fileUrl = s3Client.uploadFile("acc_doc", multipartFile); // 파일 저장
        accident.addAccidentDocumentFile(docType, fileUrl); // accident's accident document file 추가
        accidentRepository.save(accident);
    }

    public CompEmployeeDto claimCompensation(int accidentId) {
        Accident accident = accidentRepository.findById(accidentId)
                .orElseThrow(AccidentIdNotFoundException::new);
        if(!accident.checkConditionForClaimCompensation(accident.getAccidentType())) {
            throw new InsufficientSubmitAccDocFileException(); // 보상금 청구에 만족하는 파일이 모두 저장되어 있는지
        }

        Employee employee = assignEmployeeService.assignCompEmployee(); // 보상처리 담당자 배정
        accident.assignEmployeeId(employee.getId()); // accident 담당자 배정
        accidentRepository.save(accident);
        return CompEmployeeDto.toDto(employee); // 보상처리 담당자 정보 리턴
    }

    public List<AccidentListInfoDto> getAccidentListOfCustomer() {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        List<Accident> accidentList = accidentRepository.findAllByCustomerId(customerId);
        if(accidentList.isEmpty()) throw new NotExistClientAccidentsException();

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
}
