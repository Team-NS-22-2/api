package com.mju.insuranceCompany.application.viewlogic.dto.customer.dto;

import com.mju.insuranceCompany.application.domain.contract.BuildingType;
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
