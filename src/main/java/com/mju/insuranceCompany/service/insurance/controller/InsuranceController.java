package com.mju.insuranceCompany.service.insurance.controller;

import com.mju.insuranceCompany.service.insurance.controller.dto.InquirePremiumResponse;
import com.mju.insuranceCompany.service.insurance.service.InsuranceService;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquireCarPremiumRequest;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquireFirePremiumRequest;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquireHealthPremiumRequest;
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
        return ResponseEntity.ok(insuranceService.getAllInsuranceList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceGuaranteeDto> getGuaranteeById(@PathVariable int id) {
        return ResponseEntity.ok(insuranceService.getInsuranceGuaranteeById(id));
    }

    @PostMapping("/inquire-health/{insId}")
    public ResponseEntity<InquirePremiumResponse> inquireHealthPremium(@PathVariable int insId, @RequestBody InquireHealthPremiumRequest requestDto) {
        return ResponseEntity.ok(insuranceService.inquireHealthPremium(insId, requestDto));
    }

    @PostMapping("/inquire-fire/{insId}")
    public ResponseEntity<InquirePremiumResponse> inquireFirePremium(@PathVariable int insId, @RequestBody InquireFirePremiumRequest requestDto) {
        return ResponseEntity.ok(insuranceService.inquireFirePremium(insId, requestDto));
    }

    @PostMapping("/inquire-car/{insId}")
    public ResponseEntity<InquirePremiumResponse> inquireCarPremium(@PathVariable int insId, @RequestBody InquireCarPremiumRequest requestDto) {
        return ResponseEntity.ok(insuranceService.inquireCarPremium(insId, requestDto));
    }



}
