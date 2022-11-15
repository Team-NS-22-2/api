package com.mju.insuranceCompany.application.domain.contract;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class FireContract extends Contract{

	private int buildingArea;
	private BuildingType buildingType;
	private Long collateralAmount;
	private boolean isActualResidence;
	private boolean isSelfOwned;

}