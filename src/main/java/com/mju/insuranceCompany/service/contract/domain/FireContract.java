package com.mju.insuranceCompany.service.contract.domain;


import com.mju.insuranceCompany.service.contract.controller.dto.FireContractDto;
import lombok.*;

import javax.persistence.Entity;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
public class FireContract extends Contract{

	private int buildingArea;
	private BuildingType buildingType;
	private Long collateralAmount;
	private Boolean isActualResidence;
	private Boolean isSelfOwned;

	public FireContract(FireContractDto dto, int customerId) {
		super(false,  dto.getPremium(), dto.getInsuranceId(), ConditionOfUw.WAIT, customerId, 0);
		this.buildingArea = dto.getBuildingArea();
		this.buildingType = dto.getBuildingType();
		this.collateralAmount = dto.getCollateralAmount();
		this.isActualResidence = dto.getIsActualResidence();
		this.isSelfOwned = dto.getIsSelfOwned();
	}

}