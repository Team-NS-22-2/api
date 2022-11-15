package com.mju.insuranceCompany.application.viewlogic.controller;

import com.mju.insuranceCompany.application.viewlogic.dto.customer.request.InquireCarPremiumRequest;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.request.InquireFirePremiumRequest;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.request.InquireHealthPremiumRequest;
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

    @GetMapping("/inquire-health/{insId}")
    public ResponseEntity<InquirePremiumResponse> inquireHealthPremium(@PathVariable int insId, @RequestBody InquireHealthPremiumRequest requestDto) {
        return ResponseEntity.ok(service.inquireHealthPremium(insId, requestDto));
    }

    @GetMapping("/inquire-fire/{insId}")
    public ResponseEntity<InquirePremiumResponse> inquireFirePremium(@PathVariable int insId, @RequestBody InquireFirePremiumRequest requestDto) {
        return ResponseEntity.ok(service.inquireFirePremium(insId, requestDto));
    }

    @GetMapping("/inquire-car/{insId}")
    public ResponseEntity<InquirePremiumResponse> inquireCarPremium(@PathVariable int insId, @RequestBody InquireCarPremiumRequest requestDto) {
        return ResponseEntity.ok(service.inquireCarPremium(insId, requestDto));
    }

}
