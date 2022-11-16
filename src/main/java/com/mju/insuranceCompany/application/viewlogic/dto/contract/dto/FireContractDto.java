package com.mju.insuranceCompany.application.viewlogic.dto.contract.dto;

import com.mju.insuranceCompany.application.domain.contract.BuildingType;
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
