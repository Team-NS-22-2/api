package com.mju.insuranceCompany.application.viewlogic.dto.customer.request;

import com.mju.insuranceCompany.application.domain.contract.BuildingType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InquireFirePremiumRequest {
    private BuildingType buildingType;
    private long collateralAmount;
}
