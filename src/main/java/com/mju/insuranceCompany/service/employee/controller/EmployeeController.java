package com.mju.insuranceCompany.service.employee.controller;

import com.mju.insuranceCompany.service.accident.controller.dto.*;
import com.mju.insuranceCompany.service.accident.domain.CompState;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.service.accident.applicationservice.interfaces.AccidentFileService;
import com.mju.insuranceCompany.service.accident.applicationservice.interfaces.AccidentReadService;
import com.mju.insuranceCompany.service.accident.applicationservice.interfaces.AccidentUpdateService;
import com.mju.insuranceCompany.service.contract.controller.dto.CustomerCarContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.CustomerFireContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.CustomerHealthContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.RegisterContractResponse;
import com.mju.insuranceCompany.service.contract.applicationservice.interfaces.ContractCreateService;
import com.mju.insuranceCompany.service.contract.applicationservice.interfaces.ContractReadService;
import com.mju.insuranceCompany.service.contract.applicationservice.interfaces.ContractUpdateService;
import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.employee.controller.dto.UnderwritingRequest;
import com.mju.insuranceCompany.service.insurance.controller.dto.*;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import com.mju.insuranceCompany.service.insurance.domain.SalesAuthFileType;
import com.mju.insuranceCompany.service.insurance.domain.SalesAuthorizationState;
import com.mju.insuranceCompany.service.insurance.applicationservice.interfaces.InsuranceCreateService;
import com.mju.insuranceCompany.service.insurance.applicationservice.interfaces.InsuranceFileService;
import com.mju.insuranceCompany.service.insurance.applicationservice.interfaces.InsuranceReadService;
import com.mju.insuranceCompany.service.insurance.applicationservice.interfaces.InsuranceUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/emp")
@RequiredArgsConstructor
public class EmployeeController {

    private final ContractCreateService contractCreateService;
    private final ContractReadService contractReadService;
    private final ContractUpdateService contractUpdateService;
    private final InsuranceCreateService insuranceCreateService;
    private final InsuranceReadService insuranceReadService;
    private final InsuranceUpdateService insuranceUpdateService;
    private final InsuranceFileService insuranceFileService;
    private final AccidentUpdateService accidentUpdateService;
    private final AccidentReadService accidentReadService;
    private final AccidentFileService accidentFileService;

//        ???????????? ??????
    @PostMapping("/sales/health/{insId}")
    public ResponseEntity<RegisterContractResponse> salesHealthInsurance(@PathVariable int insId, @RequestBody CustomerHealthContractDto request) {
        return ResponseEntity.ok(contractCreateService.registerHealthContract(insId, request));
    }

//        ???????????? ??????
    @PostMapping("/sales/fire/{insId}")
    public ResponseEntity<RegisterContractResponse> salesFireInsurance(@PathVariable int insId, @RequestBody CustomerFireContractDto request) {
        return ResponseEntity.ok(contractCreateService.registerFireContract(insId, request));
    }

//        ??????????????? ??????
    @PostMapping("/sales/car/{insId}")
    public ResponseEntity<RegisterContractResponse> salesCarInsurance(@PathVariable int insId, @RequestBody CustomerCarContractDto request) {
        return ResponseEntity.ok(contractCreateService.registerCarContract(insId, request));
    }

//        ????????????
    @PatchMapping("/uw/{contractId}")
    public ResponseEntity<Void> underwriting(@PathVariable int contractId, @RequestBody UnderwritingRequest request) {
        contractUpdateService.underwriting(contractId, request.getReasonOfUw(), request.getConditionOfUw());
        return ResponseEntity.ok().build();
    }

//        ?????? ?????????????????? ?????? ??????
    @GetMapping("/uw/{insType}")
    public ResponseEntity<List<ConditionOfUwOfCustomerResponse>> getUwStateOfCustomer(@PathVariable InsuranceType insType) {
        return ResponseEntity.ok(contractReadService.getUwStateOfCustomer(insType));
    }

//        ?????? ???????????? ?????? ??????
    @GetMapping("/uw/health/{contractId}")
    public ResponseEntity<CustomerHealthContractDto> getHealthContractOfCustomer(@PathVariable int contractId) {
        return ResponseEntity.ok(contractReadService.getHealthContractOfCustomerByContractId(contractId));
    }

//        ?????? ???????????? ?????? ??????
    @GetMapping("/uw/fire/{contractId}")
    public ResponseEntity<CustomerFireContractDto> getFireContractOfCustomer(@PathVariable int contractId) {
        return ResponseEntity.ok(contractReadService.getFireContractOfCustomerByContractId(contractId));
    }

//        ?????? ??????????????? ?????? ??????
    @GetMapping("/uw/car/{contractId}")
    public ResponseEntity<CustomerCarContractDto> getCarContractOfCustomer(@PathVariable int contractId) {
        return ResponseEntity.ok(contractReadService.getCarContractOfCustomerByContractId(contractId));
    }

    /**
     * ?????? ?????? ??????????????? ??????
     * @return InsuranceOfDeveloperDto List
     */
    @GetMapping("/dev/list")
    public ResponseEntity<List<InsuranceOfDeveloperDto>> getInsuranceListOfDeveloper() {
        return ResponseEntity.ok(insuranceReadService.getInsuranceListOfDeveloper());
    }

    /**
     * ??????????????? ??????
     * @param calculateHealthPremiumDto ???????????? Dto, ???????????? ?????? Dto
     * @return ???????????????
     */
    @PostMapping("/dev/health-premium")
    public ResponseEntity<InsurancePremiumDto> calculateHealthPremium(@RequestBody CalculateHealthPremiumDto calculateHealthPremiumDto) {
        return ResponseEntity.ok(insuranceReadService.calculateHealthPremium(calculateHealthPremiumDto));
    }

    /**
     * ?????????????????? ??????
     * @param calculateCarPremiumDto ???????????? Dto, ??????????????? ?????? Dto
     * @return ??????????????????
     */
    @PostMapping("/dev/car-premium")
    public ResponseEntity<InsurancePremiumDto> calculateCarPremium(@RequestBody CalculateCarPremiumDto calculateCarPremiumDto) {
        return ResponseEntity.ok(insuranceReadService.calculateCarPremium(calculateCarPremiumDto));
    }

    /**
     * ??????????????? ??????
     * @param calculateFirePremiumDto ???????????? Dto, ???????????? ?????? Dto
     * @return ???????????????
     */
    @PostMapping("/dev/fire-premium")
    public ResponseEntity<InsurancePremiumDto> calculateFirePremium(@RequestBody CalculateFirePremiumDto calculateFirePremiumDto) {
        return ResponseEntity.ok(insuranceReadService.calculateFirePremium(calculateFirePremiumDto));
    }

    /**
     * ???????????? ??????
     * @param saveHealthInsuranceDto ?????????????????? Dto, ?????? Dto List, ???????????? ?????? Dto List
     * @return no contents
     */
    @PostMapping("/dev/save-health")
    public ResponseEntity<Void> saveHealthInsurance(@RequestBody SaveHealthInsuranceDto saveHealthInsuranceDto) {
        insuranceCreateService.saveHealthInsurance(saveHealthInsuranceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * ??????????????? ??????
     * @param saveCarInsuranceDto ?????????????????? Dto, ?????? Dto List, ??????????????? ?????? Dto List
     * @return no contents
     */
    @PostMapping("/dev/save-car")
    public ResponseEntity<Void> saveCarInsurance(@RequestBody SaveCarInsuranceDto saveCarInsuranceDto) {
        insuranceCreateService.saveCarInsurance(saveCarInsuranceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * ???????????? ??????
     * @param saveFireInsuranceDto ?????????????????? Dto, ?????? Dto List, ???????????? ?????? Dto List
     * @return no contents
     */
    @PostMapping("/dev/save-fire")
    public ResponseEntity<Void> saveFireInsurance(@RequestBody SaveFireInsuranceDto saveFireInsuranceDto) {
        insuranceCreateService.saveFireInsurance(saveFireInsuranceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * ???????????? ????????? ?????? ?????? ??????
     * @param insId ?????? ID
     * @return InsuranceForUploadAuthFileDto
     */
    @GetMapping("/dev/auth-file/{insId}")
    public ResponseEntity<InsuranceForUploadAuthFileDto> getInsuranceInfoForUploadAuthFile(@PathVariable int insId) {
        return ResponseEntity.ok(insuranceReadService.getInsuranceInfoForUploadAuthFile(insId));
    }

    /**
     * ?????????????????? ??????
     * @param type ?????????????????? ??????
     * @param insId ?????? id
     * @param multipartFile ????????? ??????????????????
     * @return UploadAuthFileResultDto(isExistAllFile)
     */
    @PostMapping("/dev/auth-file/{type}/{insId}")
    public ResponseEntity<UploadAuthFileResultDto> uploadSalesAuthorizationFile(
            @PathVariable SalesAuthFileType type, @PathVariable int insId, @RequestBody MultipartFile multipartFile) {
        return ResponseEntity.ok(insuranceFileService.uploadAuthFile(insId, multipartFile, type));
    }

    /**
     * ?????????????????? ??????
     * @param type ?????????????????? ??????
     * @param insId ?????? id
     * @param multipartFile ????????? ??????????????????
     * @return UploadAuthFileResultDto(isExistAllFile)
     */
    @PatchMapping("/dev/auth-file/{type}/{insId}")
    public ResponseEntity<UploadAuthFileResultDto> updateSalesAuthorizationFile(
            @PathVariable SalesAuthFileType type, @PathVariable int insId, @RequestBody MultipartFile multipartFile) {
        return ResponseEntity.ok(insuranceFileService.updateAuthFile(insId, multipartFile, type));
    }

    /**
     * ?????????????????? ??????
     * @param type ?????????????????? ??????
     * @param insId ?????? id
     * @return no content
     */
    @DeleteMapping("/dev/auth-file/{type}/{insId}")
    public ResponseEntity<Void> deleteSalesAuthorizationFile(@PathVariable SalesAuthFileType type, @PathVariable int insId) {
        insuranceFileService.deleteAuthFile(insId, type);
        return ResponseEntity.noContent().build();
    }

    /**
     * ?????????????????? ??????
     * @param insuranceId ?????? id
     * @param salesAuthorizationState ????????? ??????????????????
     * @return no content
     */
    @PatchMapping("/dev/update-auth-state/{insuranceId}")
    public ResponseEntity<Void> updateSalesAuthorizationState(@PathVariable int insuranceId, @RequestBody SalesAuthorizationState salesAuthorizationState) {
        insuranceUpdateService.updateSalesAuthorizationState(insuranceId, salesAuthorizationState);
        return ResponseEntity.ok().build();
    }

    /** ???????????? ?????? ?????? ????????? ?????? */
    @GetMapping("/comp/list")
    public ResponseEntity<List<AccidentListInfoDto>> getAccidentListOfCompEmployee(){
        return ResponseEntity.ok(accidentReadService.getAccidentListOfCompEmployee());
    }

    /** ???????????? ?????? ?????? ?????? ??? ????????? ?????? */
    @GetMapping("/comp/list/{state}")
    public ResponseEntity<List<AccidentListInfoDto>> getAccidentListOfCompEmployeeByCompState(@PathVariable("state")CompState compState){
       return ResponseEntity.ok(accidentReadService.getAccidentListOfCompEmployeeByCompState(compState));
    }

    /** ???????????? ????????? ?????? ?????? ?????? */
    @GetMapping("/comp/car-accident/{accidentId}")
    public ResponseEntity<CompCarAccidentDto> getCarAccidentOfCompEmployee(@PathVariable int accidentId) {
        return ResponseEntity.ok(accidentReadService.getCarAccidentOfCompEmployee(accidentId));
    }

    /** ???????????? ?????? ?????? ?????? ?????? */
    @GetMapping("/comp/fire-accident/{accidentId}")
    public ResponseEntity<CompFireAccidentDto> getFireAccidentOfCompEmployee(@PathVariable int accidentId) {
        return ResponseEntity.ok(accidentReadService.getFireAccidentOfCompEmployee(accidentId));
    }

    /** ???????????? ?????? ?????? ?????? ?????? */
    @GetMapping("/comp/injury-accident/{accidentId}")
    public ResponseEntity<CompInjuryAccidentDto> getInjuryAccidentOfCompEmployee(@PathVariable int accidentId) {
        return ResponseEntity.ok(accidentReadService.getInjuryAccidentOfCompEmployee(accidentId));
    }

    /** ???????????? */
    @PostMapping("/comp/investigate/{accidentId}")
    public ResponseEntity<Void> investigateAccident(@PathVariable int accidentId, @RequestBody InvestigateAccidentDto dto) {
        accidentUpdateService.investigateAccident(accidentId, dto);
        return ResponseEntity.noContent().build();
    }

    /** ???????????? ????????? ?????? */
    @PostMapping("/comp/submit/investigate-accident/{accidentId}")
    public ResponseEntity<Void> submitInvestigateAccidentFileByCompEmployee(@PathVariable int accidentId, @RequestBody MultipartFile multipartFile) {
        accidentFileService.submitAccDocFileByCompEmployee(accidentId, multipartFile, AccDocType.INVESTIGATE_ACCIDENT);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /** ??????????????? ?????? */
    @PostMapping("/comp/submit/loss-assessment/{accidentId}")
    public ResponseEntity<Void> submitLossAssessmentFileByCompEmployee(@PathVariable int accidentId, @RequestBody MultipartFile multipartFile) {
        accidentFileService.submitAccDocFileByCompEmployee(accidentId, multipartFile, AccDocType.LOSS_ASSESSMENT);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /** ????????? ?????? */
    @PostMapping("/comp/pay/{accidentId}")
    public ResponseEntity<PaymentOfCompensationResultDto> payCompensation(@PathVariable int accidentId, @RequestBody PaymentOfCompensationDto dto) {
        return ResponseEntity.ok(accidentUpdateService.payCompensation(accidentId, dto));
    }

}
