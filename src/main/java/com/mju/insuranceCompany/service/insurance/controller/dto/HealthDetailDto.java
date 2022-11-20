package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.*;

@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class HealthDetailDto extends InsuranceDetailDto {
    private int targetAge;
    private Boolean targetSex;
    private Boolean riskCriterion;
}
