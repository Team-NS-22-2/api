package com.mju.insuranceCompany.application.viewlogic.dto.insurance.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HealthDetailDto extends InsuranceDetailDto {
    private int targetAge;
    private boolean targetSex;
    private boolean riskCriterion;
}
