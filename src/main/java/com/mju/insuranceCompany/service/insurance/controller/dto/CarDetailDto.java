package com.mju.insuranceCompany.service.insurance.controller.dto;

import com.mju.insuranceCompany.service.insurance.domain.CarDetail;
import lombok.*;

@Getter @Setter @ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class CarDetailDto extends InsuranceDetailDto {
    private int targetAge;
    private long valueCriterion;

    public CarDetail toEntity() {
        return (CarDetail) new CarDetail()
                .setTargetAge(this.getTargetAge())
                .setValueCriterion(this.getValueCriterion())
                .setPremium(this.getPremium());
    }
}
