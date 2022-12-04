package com.mju.insuranceCompany.service.contract.domain;


import com.mju.insuranceCompany.service.contract.controller.dto.FireContractDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:23
 */
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class FireContract extends Contract{

	private int buildingArea;
	@Enumerated(value = EnumType.STRING)
	private BuildingType buildingType;
	private long collateralAmount;
	private boolean isActualResidence;
	private boolean isSelfOwned;

	public FireContract(FireContractDto dto, int customerId) {
		super(false,  dto.getPremium(), dto.getInsuranceId(), ConditionOfUw.WAIT, customerId, dto.getEmployeeId());
		this.buildingArea = dto.getBuildingArea();
		this.buildingType = dto.getBuildingType();
		this.collateralAmount = dto.getCollateralAmount();
		this.isActualResidence = dto.getIsActualResidence();
		this.isSelfOwned = dto.getIsSelfOwned();
	}

}