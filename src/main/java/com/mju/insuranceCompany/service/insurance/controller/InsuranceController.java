package com.mju.insuranceCompany.service.insurance.controller;

import com.mju.insuranceCompany.service.insurance.controller.dto.*;
import com.mju.insuranceCompany.service.insurance.applicationservice.interfaces.InsuranceReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceReadService insuranceReadService;

    @GetMapping("/all")
    public ResponseEntity<List<InsuranceListDto>> getInsuranceList() {
        return ResponseEntity.ok(insuranceReadService.getAllInsuranceListForSale());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceGuaranteeDto> getGuaranteeById(@PathVariable int id) {
        return ResponseEntity.ok(insuranceReadService.getInsuranceGuaranteeById(id));
    }

    @PostMapping("/inquire-health/{insId}")
    public ResponseEntity<InsurancePremiumDto> inquireHealthPremium(@PathVariable int insId, @RequestBody InquireHealthPremiumDto requestDto) {
        return ResponseEntity.ok(insuranceReadService.inquireHealthPremium(insId, requestDto));
    }

    @PostMapping("/inquire-fire/{insId}")
    public ResponseEntity<InsurancePremiumDto> inquireFirePremium(@PathVariable int insId, @RequestBody InquireFirePremiumDto requestDto) {
        return ResponseEntity.ok(insuranceReadService.inquireFirePremium(insId, requestDto));
    }

    @PostMapping("/inquire-car/{insId}")
    public ResponseEntity<InsurancePremiumDto> inquireCarPremium(@PathVariable int insId, @RequestBody InquireCarPremiumDto requestDto) {
        return ResponseEntity.ok(insuranceReadService.inquireCarPremium(insId, requestDto));
    }



}
