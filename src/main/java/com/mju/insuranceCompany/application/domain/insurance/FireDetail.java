package com.mju.insuranceCompany.application.domain.insurance;


import com.mju.insuranceCompany.application.domain.contract.BuildingType;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:39:00
 */
public class FireDetail extends InsuranceDetail {

	private BuildingType targetBuildingType;
	private long collateralAmountCriterion;

	
	public FireDetail(){
	}

	public BuildingType getTargetBuildingType() {
		return targetBuildingType;
	}

	public FireDetail setTargetBuildingType(BuildingType targetBuildingType) {
		this.targetBuildingType = targetBuildingType;
		return this;
	}

	public long getCollateralAmountCriterion() {
		return collateralAmountCriterion;
	}

	public FireDetail setCollateralAmountCriterion(long collateralAmountCriterion) {
		this.collateralAmountCriterion = collateralAmountCriterion;
		return this;
	}

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

	public String print() {
		return "화재보험 정보 {" +
				"화재보험정보 ID:" + getId() +
				", 보험료: " + getPremium() +
				", 대상건물종류: " + targetBuildingType +
				", 담보금액 기준: " + collateralAmountCriterion +
				", 보험ID: " + getInsuranceId() +
				"}";
	}
}