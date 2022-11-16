package com.mju.insuranceCompany.application.viewlogic.controller;

import com.mju.insuranceCompany.application.viewlogic.dto.insurance.dto.InsuranceListDto;
import com.mju.insuranceCompany.application.viewlogic.dto.insurance.response.InsuranceGuaranteeResponse;
import com.mju.insuranceCompany.application.viewlogic.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<InsuranceGuaranteeResponse> getGuaranteeById(@PathVariable int id) {
        return ResponseEntity.ok(insuranceService.getInsuranceGuaranteeById(id));
    }



}
