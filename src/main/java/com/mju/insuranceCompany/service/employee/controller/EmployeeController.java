package com.mju.insuranceCompany.service.employee.controller;

import com.mju.insuranceCompany.service.contract.controller.dto.RegisterCarContractRequest;
import com.mju.insuranceCompany.service.contract.controller.dto.RegisterContractResponse;
import com.mju.insuranceCompany.service.contract.controller.dto.RegisterFireContractRequest;
import com.mju.insuranceCompany.service.contract.controller.dto.RegisterHealthContractRequest;
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
//    /emp/sales/health/{insId}
    @PostMapping("/sales/health/{insId}")
    public ResponseEntity<RegisterContractResponse> salesHealthInsurance(@PathVariable int insId, @RequestBody RegisterHealthContractRequest request) {
        return ResponseEntity.ok(contractCreateService.registerHealthContract(insId, request));
    }

//        화재보험 판매
//    /emp/sales/fire/{insId}
    @PostMapping("/sales/fire/{insId}")
    public ResponseEntity<RegisterContractResponse> salesFireInsurance(@PathVariable int insId, @RequestBody RegisterFireContractRequest request) {
        return ResponseEntity.ok(contractCreateService.registerFireContract(insId, request));
    }

//        자동차보험 판매
//    /emp/sales/car/{insId}
    @PostMapping("/sales/car/{insId}")
    public ResponseEntity<RegisterContractResponse> salesCarInsurance(@PathVariable int insId, @RequestBody RegisterCarContractRequest request) {
        return ResponseEntity.ok(contractCreateService.registerCarContract(insId, request));
    }

//        인수심사
//    /emp/uw/{insId}
    public void underwriting() {

    }

//        고객 인수심사상태 정보 조회
//    /emp/uw/{insType}
    @GetMapping("/uw/{insType}")
    public ResponseEntity<List<ConditionOfUwOfCustomerResponse>> getUwStateOfCustomer(@PathVariable InsuranceType insType) {
        return ResponseEntity.ok(contractService.getUwStateOfCustomer(insType));
    }

//        고객 건강보험 계약 조회
//    /emp/uw/health/{cId}
    public void getHealthContractOfCustomer() {

    }

//        고객 화재보험 계약 조회
//    /emp/uw/fire/{cId}
    public void getFireContractOfCustomer() {

    }

//        고객 자동차보험 계약 조회
//    /emp/uw/car/{cId}
    public void getCarContractOfCustomer() {

    }

}
