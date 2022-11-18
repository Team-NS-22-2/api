package com.mju.insuranceCompany.service.employee.controller;

import com.mju.insuranceCompany.service.contract.service.ContractCreateService;
import com.mju.insuranceCompany.service.customer.controller.dto.RegisterContractResponse;
import com.mju.insuranceCompany.service.customer.controller.dto.RegisterHealthContractRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emp")
@RequiredArgsConstructor
public class EmployeeController {

    private final ContractCreateService contractCreateService;

//        건강보험 판매
//    /emp/sales/health/{insId}
    @PostMapping("/sales/health/{insId}")
    public ResponseEntity<RegisterContractResponse> salesHealthInsurance(@PathVariable int insId, @RequestBody RegisterHealthContractRequest request) {
        return ResponseEntity.ok(contractCreateService.registerHealthContract(insId, request));
    }

//        화재보험 판매
//    /emp/sales/fire/{insId}
    public void salesFireInsurance() {

    }

//        자동차보험 판매
//    /emp/sales/car/{insId}
    public void salesCarInsurance() {

    }

//        인수심사
//    /emp/uw/{insId}
    public void underwriting() {

    }

//        고객 인수심사상태 정보 조회
//    /emp/uw/{insType}
    public void getUwStateOfCustomer() {

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
