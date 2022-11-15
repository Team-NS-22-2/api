package com.mju.insuranceCompany.application.viewlogic.controller;

import com.mju.insuranceCompany.application.viewlogic.dto.customer.request.InquireHealthPremiumRequestDto;
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
    public ResponseEntity<Integer> inquireHealthPremium(@PathVariable int insId, @RequestBody InquireHealthPremiumRequestDto requestDto) {
        return ResponseEntity.ok(service.inquireHealthPremium(insId, requestDto));
    }

}
