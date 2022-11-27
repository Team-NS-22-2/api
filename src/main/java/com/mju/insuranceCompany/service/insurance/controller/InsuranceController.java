package com.mju.insuranceCompany.service.insurance.controller;

import com.mju.insuranceCompany.service.insurance.controller.dto.InsurancePremiumDto;
import com.mju.insuranceCompany.service.insurance.service.InsuranceService;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquireCarPremiumDto;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquireFirePremiumDto;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquireHealthPremiumDto;
import com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceListDto;
import com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceGuaranteeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @GetMapping("/all")
    public ResponseEntity<List<InsuranceListDto>> getInsuranceList() {
        return ResponseEntity.ok(insuranceService.getAllInsuranceListForSale());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceGuaranteeDto> getGuaranteeById(@PathVariable int id) {
        return ResponseEntity.ok(insuranceService.getInsuranceGuaranteeById(id));
    }

    @PostMapping("/inquire-health/{insId}")
    public ResponseEntity<InsurancePremiumDto> inquireHealthPremium(@PathVariable int insId, @RequestBody InquireHealthPremiumDto requestDto) {
        return ResponseEntity.ok(insuranceService.inquireHealthPremium(insId, requestDto));
    }

    @PostMapping("/inquire-fire/{insId}")
    public ResponseEntity<InsurancePremiumDto> inquireFirePremium(@PathVariable int insId, @RequestBody InquireFirePremiumDto requestDto) {
        return ResponseEntity.ok(insuranceService.inquireFirePremium(insId, requestDto));
    }

    @PostMapping("/inquire-car/{insId}")
    public ResponseEntity<InsurancePremiumDto> inquireCarPremium(@PathVariable int insId, @RequestBody InquireCarPremiumDto requestDto) {
        return ResponseEntity.ok(insuranceService.inquireCarPremium(insId, requestDto));
    }



}
