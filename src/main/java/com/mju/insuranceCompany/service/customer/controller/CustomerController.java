package com.mju.insuranceCompany.service.customer.controller;

import com.mju.insuranceCompany.service.contract.service.ContractService;
import com.mju.insuranceCompany.service.customer.controller.dto.ContractReceiptDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/customer")
@RestController
public class CustomerController {

    private final ContractService contractService;

    @GetMapping("/contract")
    public ResponseEntity<List<ContractReceiptDto>> getAllContractReceipts(){
        return ResponseEntity.ok(contractService.getAllContractReceipts());
    }

}
