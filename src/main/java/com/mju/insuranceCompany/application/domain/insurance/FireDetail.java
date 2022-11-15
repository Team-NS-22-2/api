package com.mju.insuranceCompany.application.domain.insurance;


import com.mju.insuranceCompany.application.domain.contract.BuildingType;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.response.FireDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:39:00
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "fire_detail_id")
public class FireDetail extends InsuranceDetail {

	private BuildingType targetBuildingType;
	private long collateralAmountCriterion;

	public FireDetailDto toDto() {
		return (FireDetailDto) FireDetailDto.builder()
				.targetBuildingType(this.getTargetBuildingType())
				.collateralAmountCriterion(this.getCollateralAmountCriterion())
				.build()
				.setPremium(getPremium());
	}

}