package com.mju.insuranceCompany.service.customer.controller;

import com.mju.insuranceCompany.service.contract.service.ContractService;
import com.mju.insuranceCompany.service.customer.controller.dto.ContractReceiptDto;
import com.mju.insuranceCompany.service.customer.controller.dto.PaymentBasicInfoDto;
import com.mju.insuranceCompany.service.customer.controller.dto.PaymentCreateDto;
import com.mju.insuranceCompany.service.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/customer")
@RestController
public class CustomerController {

    private final ContractService contractService;
    private final CustomerService customerService;

    @GetMapping("/contract")
    public ResponseEntity<List<ContractReceiptDto>> getAllContractReceipts(){
        return ResponseEntity.ok(contractService.getAllContractReceipts());
    }

    @GetMapping("/payment")
    public ResponseEntity<List<PaymentBasicInfoDto>> getAllPaymentInfos(){
        return ResponseEntity.ok(customerService.getAllPaymentInfos());
    }

    @PostMapping("/payment")
    public ResponseEntity<Void> addNewPayment(@RequestBody PaymentCreateDto dto){
        customerService.addNewPayment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
//    @PatchMapping("/payment")

}
