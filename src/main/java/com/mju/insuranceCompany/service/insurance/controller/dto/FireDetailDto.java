package com.mju.insuranceCompany.service.insurance.controller.dto;

import com.mju.insuranceCompany.service.contract.domain.BuildingType;
import com.mju.insuranceCompany.service.insurance.domain.FireDetail;
import lombok.*;

@Getter @Setter @ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class FireDetailDto extends InsuranceDetailDto {
    private BuildingType targetBuildingType;
    private long collateralAmountCriterion;

    public FireDetail toEntity() {
        return (FireDetail) new FireDetail()
                .setTargetBuildingType(this.getTargetBuildingType())
                .setCollateralAmountCriterion(this.getCollateralAmountCriterion())
                .setPremium(this.getPremium());
    }
}
