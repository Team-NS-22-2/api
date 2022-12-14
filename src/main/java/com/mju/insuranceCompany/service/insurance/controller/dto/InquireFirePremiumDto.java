package com.mju.insuranceCompany.service.insurance.controller.dto;

import com.mju.insuranceCompany.service.contract.domain.BuildingType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InquireFirePremiumDto {
    private BuildingType buildingType;
    private long collateralAmount;
}
