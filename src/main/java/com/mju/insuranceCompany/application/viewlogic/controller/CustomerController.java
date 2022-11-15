package com.mju.insuranceCompany.application.viewlogic.controller;

import com.mju.insuranceCompany.application.viewlogic.dto.customer.request.*;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.response.InquirePremiumResponse;
import com.mju.insuranceCompany.application.viewlogic.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cust")
public class CustomerController {

    private final CustomerService service;

    @PostMapping("/inquire-health/{insId}")
    public ResponseEntity<InquirePremiumResponse> inquireHealthPremium(@PathVariable int insId, @RequestBody InquireHealthPremiumRequest requestDto) {
        return ResponseEntity.ok(service.inquireHealthPremium(insId, requestDto));
    }

    @PostMapping("/inquire-fire/{insId}")
    public ResponseEntity<InquirePremiumResponse> inquireFirePremium(@PathVariable int insId, @RequestBody InquireFirePremiumRequest requestDto) {
        return ResponseEntity.ok(service.inquireFirePremium(insId, requestDto));
    }

    @PostMapping("/inquire-car/{insId}")
    public ResponseEntity<InquirePremiumResponse> inquireCarPremium(@PathVariable int insId, @RequestBody InquireCarPremiumRequest requestDto) {
        return ResponseEntity.ok(service.inquireCarPremium(insId, requestDto));
    }

//    // /register-health/{insId}
    @PostMapping("/register-health/{insId}")
    public ResponseEntity registerHealthContract(@PathVariable int insId, @RequestBody RegisterHealthContractRequest request) {
        service.registerHealthContract(insId, request);
        return ResponseEntity.noContent().build();
    }

//    // /register-fire/{insId}
    @PostMapping("/register-fire/{insId}")
    public ResponseEntity registerFireContract(@PathVariable int insId, @RequestBody RegisterFireContractRequest request) {
        service.registerFireContract(insId, request);
        return ResponseEntity.noContent().build();
    }

//    // /register-car/{insId}
    @PostMapping("/register-car/{insId}")
    public ResponseEntity registerCarContract(@PathVariable int insId, @RequestBody RegisterCarContractRequest request) {
        service.registerCarContract(insId, request);
        return ResponseEntity.noContent().build();
    }

}
