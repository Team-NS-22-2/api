package com.mju.insuranceCompany.service.insurance.controller.dto;

import com.mju.insuranceCompany.service.contract.domain.BuildingType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class FireDetailDto extends InsuranceDetailDto {
    private BuildingType targetBuildingType;
    private long collateralAmountCriterion;
}
