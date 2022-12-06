package com.mju.insuranceCompany.service.insurance.controller.dto;

import com.mju.insuranceCompany.service.insurance.domain.HealthDetail;
import lombok.*;

@Getter @Setter @ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class HealthDetailDto extends InsuranceDetailDto {
    private int targetAge;
    private Boolean targetSex;
    private Boolean riskCriterion;

    public HealthDetail toEntity() {
        return (HealthDetail) new HealthDetail()
                .setTargetAge(this.getTargetAge())
                .setTargetSex(this.getTargetSex())
                .setRiskCriterion(this.getRiskCriterion())
                .setPremium(this.getPremium());
    }
}
