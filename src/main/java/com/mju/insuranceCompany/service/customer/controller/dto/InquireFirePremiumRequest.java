package com.mju.insuranceCompany.service.customer.controller.dto;

import com.mju.insuranceCompany.service.contract.domain.BuildingType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InquireFirePremiumRequest {
    private BuildingType buildingType;
    private long collateralAmount;
}
