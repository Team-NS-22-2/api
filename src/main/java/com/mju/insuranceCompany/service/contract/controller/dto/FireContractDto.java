package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.contract.domain.BuildingType;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FireContractDto extends ContractDto {

    private int buildingArea;
    private BuildingType buildingType;
    private Long collateralAmount;
    private Boolean isActualResidence;
    private Boolean isSelfOwned;

}
