package com.mju.insuranceCompany.service.customer.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InquireHealthPremiumRequest {
    private String ssn;
    private int riskCount;
}
