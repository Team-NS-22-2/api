package com.mju.insuranceCompany.service.employee.controller;

import com.mju.insuranceCompany.service.contract.controller.dto.CustomerCarContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.CustomerFireContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.CustomerHealthContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.RegisterContractResponse;
import com.mju.insuranceCompany.service.contract.service.ContractCreateService;
import com.mju.insuranceCompany.service.contract.service.ContractService;
import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
@RequiredArgsConstructor
public class EmployeeController {

    private final ContractCreateService contractCreateService;
    private final ContractService contractService;

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
    @GetMapping("/uw/health/{customerId}")
    public ResponseEntity<CustomerHealthContractDto> getHealthContractOfCustomer(@PathVariable int customerId) {
        return ResponseEntity.ok(contractService.getHealthContractOfCustomer(customerId));
    }

//        고객 화재보험 계약 조회
    @GetMapping("/uw/fire/{customerId}")
    public ResponseEntity<CustomerFireContractDto> getFireContractOfCustomer(@PathVariable int customerId) {
        return ResponseEntity.ok(contractService.getFireContractOfCustomer(customerId));
    }

//        고객 자동차보험 계약 조회
    @GetMapping("/uw/car/{customerId}")
    public ResponseEntity<CustomerCarContractDto> getCarContractOfCustomer(@PathVariable int customerId) {
        return ResponseEntity.ok(contractService.getCarContractOfCustomer(customerId));
    }

}
