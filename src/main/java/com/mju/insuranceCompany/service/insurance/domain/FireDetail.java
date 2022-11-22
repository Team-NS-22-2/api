package com.mju.insuranceCompany.service.insurance.domain;


import com.mju.insuranceCompany.service.contract.domain.BuildingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:39:00
 */
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "fire_detail_id")
public class FireDetail extends InsuranceDetail {

	@Enumerated(value = EnumType.STRING)
	private BuildingType targetBuildingType;
	private long collateralAmountCriterion;

}