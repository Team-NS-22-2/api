package com.mju.insuranceCompany.application.viewlogic.dto.contract;

import com.mju.insuranceCompany.application.domain.contract.BuildingType;
import lombok.*;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FireContractDto extends ContractDto {

    private int buildingArea;
    private BuildingType buildingType;
    private Long collateralAmount;
    private boolean isActualResidence;
    private boolean isSelfOwned;

}
