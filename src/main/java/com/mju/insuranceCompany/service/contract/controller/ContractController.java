package com.mju.insuranceCompany.service.contract.controller;

import com.mju.insuranceCompany.service.customer.controller.dto.RegisterCarContractRequest;
import com.mju.insuranceCompany.service.customer.controller.dto.RegisterFireContractRequest;
import com.mju.insuranceCompany.service.customer.controller.dto.RegisterHealthContractRequest;
import com.mju.insuranceCompany.service.customer.controller.dto.RegisterContractResponse;
import com.mju.insuranceCompany.service.contract.service.ContractCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractCreateService contractCreateService;

    // /register-health/{insId}
    @PostMapping("/register-health/{insId}")
    public ResponseEntity<RegisterContractResponse> registerHealthContract(@PathVariable int insId, @RequestBody RegisterHealthContractRequest request) {
        return ResponseEntity.ok(contractCreateService.registerHealthContract(insId, request));
    }

    //    // /register-fire/{insId}
    @PostMapping("/register-fire/{insId}")
    public ResponseEntity<RegisterContractResponse> registerFireContract(@PathVariable int insId, @RequestBody RegisterFireContractRequest request) {
        return ResponseEntity.ok(contractCreateService.registerFireContract(insId, request));
    }

    //    // /register-car/{insId}
    @PostMapping("/register-car/{insId}")
    public ResponseEntity<RegisterContractResponse> registerCarContract(@PathVariable int insId, @RequestBody RegisterCarContractRequest request) {
        return ResponseEntity.ok(contractCreateService.registerCarContract(insId, request));
    }

}
