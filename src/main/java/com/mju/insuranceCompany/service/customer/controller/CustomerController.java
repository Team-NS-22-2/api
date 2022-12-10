package com.mju.insuranceCompany.service.customer.controller;

import com.mju.insuranceCompany.service.contract.controller.dto.PaymentRegisterOnContractDto;
import com.mju.insuranceCompany.service.contract.service.interfaces.ContractPayService;
import com.mju.insuranceCompany.service.contract.service.interfaces.ContractReadService;
import com.mju.insuranceCompany.service.contract.service.interfaces.PaymentRegisterService;
import com.mju.insuranceCompany.service.customer.controller.dto.ContractReceiptDto;
import com.mju.insuranceCompany.service.customer.controller.dto.PaymentBasicInfoDto;
import com.mju.insuranceCompany.service.customer.controller.dto.PaymentCreateDto;
import com.mju.insuranceCompany.service.customer.service.interfaces.CustomerReadService;
import com.mju.insuranceCompany.service.customer.service.interfaces.CustomerUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/customer")
@RestController
public class CustomerController {

    private final ContractReadService contractService;
    private final CustomerReadService customerReadService;
    private final CustomerUpdateService customerUpdateService;
    private final PaymentRegisterService paymentRegisterService;
    private final ContractPayService contractPayService;

    @GetMapping("/contract")
    public ResponseEntity<List<ContractReceiptDto>> getAllContractReceipts(){
        return ResponseEntity.ok(contractService.getAllContractReceipts());
    }

    @GetMapping("/payment")
    public ResponseEntity<List<PaymentBasicInfoDto>> getAllPaymentInfos(){
        return ResponseEntity.ok(customerReadService.getAllPaymentInfos());
    }

    @PostMapping("/payment")
    public ResponseEntity<Void> addNewPayment(@RequestBody PaymentCreateDto dto){
        customerUpdateService.addNewPayment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PatchMapping("/payment")
    public ResponseEntity<Void> registerPaymentOnContract(@RequestBody PaymentRegisterOnContractDto dto){
        paymentRegisterService.registerPaymentOnContract(dto);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/pay/{contractId}")
    public ResponseEntity<Void> payContractPremium(@PathVariable("contractId") int contractId){
        contractPayService.payForContractPremium(contractId);
        return ResponseEntity.noContent().build();
    }

}
