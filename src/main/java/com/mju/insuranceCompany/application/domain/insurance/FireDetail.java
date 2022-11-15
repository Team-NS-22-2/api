package com.mju.insuranceCompany.application.domain.insurance;


import com.mju.insuranceCompany.application.domain.contract.BuildingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:39:00
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FireDetail extends InsuranceDetail {

	private BuildingType targetBuildingType;
	private long collateralAmountCriterion;

	
	public String printCollateralAmountCriterion() {
		String CollateralAmountRange;
		if(collateralAmountCriterion == 5000000000L) CollateralAmountRange = "50억원 이상";
		else if(collateralAmountCriterion == 1000000000L) CollateralAmountRange = "10억원 ~ 50억원";
		else if(collateralAmountCriterion == 500000000L) CollateralAmountRange = "5억원 ~ 10억원";
		else if(collateralAmountCriterion == 100000000L) CollateralAmountRange = "1억 ~ 5억원";
		else CollateralAmountRange = "1억원 이하";
		return CollateralAmountRange;
	}

	public String printBuildingType() {
		String type = switch (targetBuildingType){
			case COMMERCIAL -> "상업용";
			case INDUSTRIAL -> "산업용";
			case INSTITUTIONAL -> "기관용";
			case RESIDENTIAL -> "주거용";
		};
		return type;
	}

}