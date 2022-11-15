package com.mju.insuranceCompany.application.viewlogic.dto.customer.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InquireHealthPremiumRequestDto {
    private String ssn;
    private int riskCount;
}
