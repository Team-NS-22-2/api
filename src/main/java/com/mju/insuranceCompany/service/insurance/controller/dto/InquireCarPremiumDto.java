package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InquireCarPremiumDto {
    private String ssn;
    private long value;
}
