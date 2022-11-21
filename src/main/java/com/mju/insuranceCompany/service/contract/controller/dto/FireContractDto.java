package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.contract.domain.BuildingType;
import com.mju.insuranceCompany.service.contract.domain.FireContract;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FireContractDto extends ContractDto {
    private int contractId;
    private int buildingArea;
    private BuildingType buildingType;
    private Long collateralAmount;
    private Boolean isActualResidence;
    private Boolean isSelfOwned;

    public static FireContractDto toDtoFromEntity(FireContract fireContract) {
        return new FireContractDto(
                fireContract.getId(),
                fireContract.getBuildingArea(),
                fireContract.getBuildingType(),
                fireContract.getCollateralAmount(),
                fireContract.getIsActualResidence(),
                fireContract.getIsSelfOwned()
        );
    }

}
