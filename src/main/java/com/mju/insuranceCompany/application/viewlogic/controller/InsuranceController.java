package com.mju.insuranceCompany.application.viewlogic.controller;

import com.mju.insuranceCompany.application.viewlogic.dto.insurance.response.InsuranceListDto;
import com.mju.insuranceCompany.application.viewlogic.dto.insurance.response.InsuranceGuaranteeDto;
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
        List<InsuranceListDto> list = insuranceService.getAllInsuranceList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceGuaranteeDto> getGuaranteeById(@PathVariable int id) {
        return ResponseEntity.ok(insuranceService.getInsuranceGuaranteeById(id));
    }



}
