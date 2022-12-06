package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InquireHealthPremiumDto {
    private String ssn;
    private int riskCount;
}
