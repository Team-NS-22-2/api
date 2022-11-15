package com.mju.insuranceCompany.application.viewlogic.dto.customer.response;

import lombok.*;

@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class HealthDetailDto extends InsuranceDetailDto {
    private int targetAge;
    private boolean targetSex;
    private boolean riskCriterion;
}
