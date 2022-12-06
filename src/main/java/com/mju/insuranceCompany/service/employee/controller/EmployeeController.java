package com.mju.insuranceCompany.service.employee.controller;

import com.mju.insuranceCompany.service.accident.controller.dto.*;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.service.accident.service.interfaces.AccidentFileService;
import com.mju.insuranceCompany.service.accident.service.interfaces.AccidentReadService;
import com.mju.insuranceCompany.service.accident.service.interfaces.AccidentUpdateService;
import com.mju.insuranceCompany.service.contract.controller.dto.CustomerCarContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.CustomerFireContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.CustomerHealthContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.RegisterContractResponse;
import com.mju.insuranceCompany.service.contract.service.ContractCreateService;
import com.mju.insuranceCompany.service.contract.service.ContractService;
import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.employee.controller.dto.UnderwritingRequest;
import com.mju.insuranceCompany.service.insurance.controller.dto.*;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import com.mju.insuranceCompany.service.insurance.domain.SalesAuthFileType;
import com.mju.insuranceCompany.service.insurance.domain.SalesAuthorizationState;
import com.mju.insuranceCompany.service.insurance.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/emp")
@RequiredArgsConstructor
public class EmployeeController {

    private final ContractCreateService contractCreateService;
    private final ContractService contractService;
    private final InsuranceService insuranceService;
    private final AccidentUpdateService accidentService;
    private final AccidentReadService accidentReadService;
    private final AccidentFileService accidentFileService;

//        건강보험 판매
    @PostMapping("/sales/health/{insId}")
    public ResponseEntity<RegisterContractResponse> salesHealthInsurance(@PathVariable int insId, @RequestBody CustomerHealthContractDto request) {
        return ResponseEntity.ok(contractCreateService.registerHealthContract(insId, request));
    }

//        화재보험 판매
    @PostMapping("/sales/fire/{insId}")
    public ResponseEntity<RegisterContractResponse> salesFireInsurance(@PathVariable int insId, @RequestBody CustomerFireContractDto request) {
        return ResponseEntity.ok(contractCreateService.registerFireContract(insId, request));
    }

//        자동차보험 판매
    @PostMapping("/sales/car/{insId}")
    public ResponseEntity<RegisterContractResponse> salesCarInsurance(@PathVariable int insId, @RequestBody CustomerCarContractDto request) {
        return ResponseEntity.ok(contractCreateService.registerCarContract(insId, request));
    }

//        인수심사
    @PatchMapping("/uw/{contractId}")
    public ResponseEntity<Void> underwriting(@PathVariable int contractId, @RequestBody UnderwritingRequest request) {
        contractService.underwriting(contractId, request.getReasonOfUw(), request.getConditionOfUw());
        return ResponseEntity.ok().build();
    }

//        고객 인수심사상태 정보 조회
    @GetMapping("/uw/{insType}")
    public ResponseEntity<List<ConditionOfUwOfCustomerResponse>> getUwStateOfCustomer(@PathVariable InsuranceType insType) {
        return ResponseEntity.ok(contractService.getUwStateOfCustomer(insType));
    }

//        고객 건강보험 계약 조회
    @GetMapping("/uw/health/{contractId}")
    public ResponseEntity<CustomerHealthContractDto> getHealthContractOfCustomer(@PathVariable int contractId) {
        return ResponseEntity.ok(contractService.getHealthContractOfCustomerByContractId(contractId));
    }

//        고객 화재보험 계약 조회
    @GetMapping("/uw/fire/{contractId}")
    public ResponseEntity<CustomerFireContractDto> getFireContractOfCustomer(@PathVariable int contractId) {
        return ResponseEntity.ok(contractService.getFireContractOfCustomerByContractId(contractId));
    }

//        고객 자동차보험 계약 조회
    @GetMapping("/uw/car/{contractId}")
    public ResponseEntity<CustomerCarContractDto> getCarContractOfCustomer(@PathVariable int contractId) {
        return ResponseEntity.ok(contractService.getCarContractOfCustomerByContractId(contractId));
    }

    /**
     * 직원 상품 개발리스트 조회
     * @return InsuranceOfDeveloperDto List
     */
    @GetMapping("/dev/list")
    public ResponseEntity<List<InsuranceOfDeveloperDto>> getInsuranceListOfDeveloper() {
        return ResponseEntity.ok(insuranceService.getInsuranceListOfDeveloper());
    }

    /**
     * 건강보험료 계산
     * @param calculateHealthPremiumDto 순보험료 Dto, 건강보험 정보 Dto
     * @return 건강보험료
     */
    @PostMapping("/dev/health-premium")
    public ResponseEntity<InsurancePremiumDto> calculateHealthPremium(@RequestBody CalculateHealthPremiumDto calculateHealthPremiumDto) {
        return ResponseEntity.ok(insuranceService.calculateHealthPremium(calculateHealthPremiumDto));
    }

    /**
     * 자동차보험료 계산
     * @param calculateCarPremiumDto 순보험료 Dto, 자동차보험 정보 Dto
     * @return 자동차보험료
     */
    @PostMapping("/dev/car-premium")
    public ResponseEntity<InsurancePremiumDto> calculateCarPremium(@RequestBody CalculateCarPremiumDto calculateCarPremiumDto) {
        return ResponseEntity.ok(insuranceService.calculateCarPremium(calculateCarPremiumDto));
    }

    /**
     * 화재보험료 계산
     * @param calculateFirePremiumDto 순보험료 Dto, 화재보험 정보 Dto
     * @return 화재보험료
     */
    @PostMapping("/dev/fire-premium")
    public ResponseEntity<InsurancePremiumDto> calculateFirePremium(@RequestBody CalculateFirePremiumDto calculateFirePremiumDto) {
        return ResponseEntity.ok(insuranceService.calculateFirePremium(calculateFirePremiumDto));
    }

    /**
     * 건강보험 저장
     * @param saveHealthInsuranceDto 보험기본정보 Dto, 보장 Dto List, 건강보험 정보 Dto List
     * @return no contents
     */
    @PostMapping("/dev/save-health")
    public ResponseEntity<Void> saveHealthInsurance(@RequestBody SaveHealthInsuranceDto saveHealthInsuranceDto) {
        insuranceService.saveHealthInsurance(saveHealthInsuranceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 자동차보험 저장
     * @param saveCarInsuranceDto 보험기본정보 Dto, 보장 Dto List, 자동차보험 정보 Dto List
     * @return no contents
     */
    @PostMapping("/dev/save-car")
    public ResponseEntity<Void> saveCarInsurance(@RequestBody SaveCarInsuranceDto saveCarInsuranceDto) {
        insuranceService.saveCarInsurance(saveCarInsuranceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 화재보험 저장
     * @param saveFireInsuranceDto 보험기본정보 Dto, 보장 Dto List, 화재보험 정보 Dto List
     * @return no contents
     */
    @PostMapping("/dev/save-fire")
    public ResponseEntity<Void> saveFireInsurance(@RequestBody SaveFireInsuranceDto saveFireInsuranceDto) {
        insuranceService.saveFireInsurance(saveFireInsuranceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 인가파일 등록할 보험 정보 조회
     * @param insId 보험 ID
     * @return InsuranceForUploadAuthFileDto
     */
    @GetMapping("/dev/auth-file/{insId}")
    public ResponseEntity<InsuranceForUploadAuthFileDto> getInsuranceInfoForUploadAuthFile(@PathVariable int insId) {
        return ResponseEntity.ok(insuranceService.getInsuranceInfoForUploadAuthFile(insId));
    }

    /**
     * 판매인가파일 등록
     * @param type 판매인가파일 타입
     * @param insId 보험 id
     * @param multipartFile 등록할 판매인가파일
     * @return UploadAuthFileResultDto(isExistAllFile)
     */
    @PostMapping("/dev/auth-file/{type}/{insId}")
    public ResponseEntity<UploadAuthFileResultDto> uploadSalesAuthorizationFile(
            @PathVariable SalesAuthFileType type, @PathVariable int insId, @RequestBody MultipartFile multipartFile) {
        return ResponseEntity.ok(insuranceService.uploadAuthFile(insId, multipartFile, type));
    }

    /**
     * 판매인가파일 수정
     * @param type 판매인가파일 타입
     * @param insId 보험 id
     * @param multipartFile 수정할 판매인가파일
     * @return UploadAuthFileResultDto(isExistAllFile)
     */
    @PatchMapping("/dev/auth-file/{type}/{insId}")
    public ResponseEntity<UploadAuthFileResultDto> updateSalesAuthorizationFile(
            @PathVariable SalesAuthFileType type, @PathVariable int insId, @RequestBody MultipartFile multipartFile) {
        return ResponseEntity.ok(insuranceService.updateAuthFile(insId, multipartFile, type));
    }

    /**
     * 판매인가파일 삭제
     * @param type 판매인가파일 타입
     * @param insId 보험 id
     * @return no content
     */
    @DeleteMapping("/dev/auth-file/{type}/{insId}")
    public ResponseEntity<Void> deleteSalesAuthorizationFile(@PathVariable SalesAuthFileType type, @PathVariable int insId) {
        insuranceService.deleteAuthFile(insId, type);
        return ResponseEntity.noContent().build();
    }

    /**
     * 판매인가상태 변경
     * @param insuranceId 보험 id
     * @param salesAuthorizationState 변경할 판매인가상태
     * @return no content
     */
    @PatchMapping("/dev/update-auth-state/{insuranceId}")
    public ResponseEntity<Void> updateSalesAuthorizationState(@PathVariable int insuranceId, @RequestBody SalesAuthorizationState salesAuthorizationState) {
        insuranceService.updateSalesAuthorizationState(insuranceId, salesAuthorizationState);
        return ResponseEntity.ok().build();
    }

    /** 보상직원 할당 사고 리스트 조회 */
    @GetMapping("/comp/list")
    public ResponseEntity<List<AccidentListInfoDto>> getAccidentListOfCompEmployee(){
        return ResponseEntity.ok(accidentReadService.getAccidentListOfCompEmployee());
    }

    /** 보상처리 자동차 사고 정보 조회 */
    @GetMapping("/comp/car-accident/{accidentId}")
    public ResponseEntity<CompCarAccidentDto> getCarAccidentOfCompEmployee(@PathVariable int accidentId) {
        return ResponseEntity.ok(accidentReadService.getCarAccidentOfCompEmployee(accidentId));
    }

    /** 보상처리 화재 사고 정보 조회 */
    @GetMapping("/comp/fire-accident/{accidentId}")
    public ResponseEntity<CompFireAccidentDto> getFireAccidentOfCompEmployee(@PathVariable int accidentId) {
        return ResponseEntity.ok(accidentReadService.getFireAccidentOfCompEmployee(accidentId));
    }

    /** 보상처리 상해 사고 정보 조회 */
    @GetMapping("/comp/injury-accident/{accidentId}")
    public ResponseEntity<CompInjuryAccidentDto> getInjuryAccidentOfCompEmployee(@PathVariable int accidentId) {
        return ResponseEntity.ok(accidentReadService.getInjuryAccidentOfCompEmployee(accidentId));
    }

    /** 손해조사 */
    @PostMapping("/comp/investigate/{accidentId}")
    public ResponseEntity<Void> investigateAccident(@PathVariable int accidentId, @RequestBody InvestigateAccidentDto dto) {
        accidentService.investigateAccident(accidentId, dto);
        return ResponseEntity.noContent().build();
    }

    /** 사고조사 보고서 제출 */
    @PostMapping("/comp/submit/investigate-accident/{accidentId}")
    public ResponseEntity<Void> submitInvestigateAccidentFileByCompEmployee(@PathVariable int accidentId, @RequestBody MultipartFile multipartFile) {
        accidentFileService.submitAccDocFileByCompEmployee(accidentId, multipartFile, AccDocType.INVESTIGATE_ACCIDENT);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /** 손해사정서 제출 */
    @PostMapping("/comp/submit/loss-assessment/{accidentId}")
    public ResponseEntity<Void> submitLossAssessmentFileByCompEmployee(@PathVariable int accidentId, @RequestBody MultipartFile multipartFile) {
        accidentFileService.submitAccDocFileByCompEmployee(accidentId, multipartFile, AccDocType.LOSS_ASSESSMENT);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /** 보상금 지급 */
    @PostMapping("/comp/pay/{accidentId}")
    public ResponseEntity<PaymentOfCompensationResultDto> payCompensation(@PathVariable int accidentId, @RequestBody PaymentOfCompensationDto dto) {
        return ResponseEntity.ok(accidentService.payCompensation(accidentId, dto));
    }

}
