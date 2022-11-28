package com.mju.insuranceCompany.service.employee.controller;

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
import com.mju.insuranceCompany.service.insurance.service.InsuranceService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity underwriting(@PathVariable int contractId, @RequestBody UnderwritingRequest request) {
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
    public ResponseEntity saveHealthInsurance(@RequestBody SaveHealthInsuranceDto saveHealthInsuranceDto) {
        insuranceService.saveHealthInsurance(saveHealthInsuranceDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * 자동차보험 저장
     * @param saveCarInsuranceDto 보험기본정보 Dto, 보장 Dto List, 자동차보험 정보 Dto List
     * @return no contents
     */
    @PostMapping("/dev/save-car")
    public ResponseEntity saveCarInsurance(@RequestBody SaveCarInsuranceDto saveCarInsuranceDto) {
        insuranceService.saveCarInsurance(saveCarInsuranceDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * 화재보험 저장
     * @param saveFireInsuranceDto 보험기본정보 Dto, 보장 Dto List, 화재보험 정보 Dto List
     * @return no contents
     */
    @PostMapping("/dev/save-fire")
    public ResponseEntity saveFireInsurance(@RequestBody SaveFireInsuranceDto saveFireInsuranceDto) {
        insuranceService.saveFireInsurance(saveFireInsuranceDto);
        return ResponseEntity.noContent().build();
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
     * 보험상품신고서 등록
     * @param insId 보험 ID
     * @param multipartFile 보험상품신고서 파일
     * @return UploadAuthFIleResultDto(isExistAllFile)
     */
    @PostMapping("/dev/auth-file/save/prod/{insId}")
    public ResponseEntity<UploadAuthFileResultDto> uploadProdDeclarationFile(@PathVariable int insId, @RequestBody MultipartFile multipartFile) {
        return ResponseEntity.ok(insuranceService.uploadProdDeclarationFile(insId, multipartFile));
    }

    /**
     * 보험요율산출기관 검증확인서 등록
     * @param insId 보험 ID
     * @param multipartFile 보험요율산출기관 검증확인서 파일
     * @return UploadAuthFIleResultDto(isExistAllFile)
     */
    @PostMapping("/dev/auth-file/save/iso/{insId}")
    public ResponseEntity<UploadAuthFileResultDto> uploadIsoVerificationFile(@PathVariable int insId, @RequestBody MultipartFile multipartFile) {
        return ResponseEntity.ok(insuranceService.uploadIsoVerificationFile(insId, multipartFile));
    }

    /**
     * 선임계리사 검증기초서류 등록
     * @param insId 보험 ID
     * @param multipartFile 선임계리사 검증기초서류 파일
     * @return UploadAuthFIleResultDto(isExistAllFile)
     */
    @PostMapping("/dev/auth-file/save/sractuary/{insId}")
    public ResponseEntity<UploadAuthFileResultDto> uploadSrActuaryVerificationFile(@PathVariable int insId, @RequestBody MultipartFile multipartFile) {
            return ResponseEntity.ok(insuranceService.uploadSrActuaryVerificationFile(insId, multipartFile));
        }

    /**
     * 금융감독원 인가허가파일 등록
     * @param insId 보험 ID
     * @param multipartFile 금융감독원 인가허가파일 파일
     * @return UploadAuthFIleResultDto(isExistAllFile)
     */
    @PostMapping("/dev/auth-file/save/fssofficial/{insId}")
    public ResponseEntity<UploadAuthFileResultDto> uploadFssOfficialDocFile(@PathVariable int insId, @RequestBody MultipartFile multipartFile) {
        return ResponseEntity.ok(insuranceService.uploadFssOfficialDocFile(insId, multipartFile));
    }

}
