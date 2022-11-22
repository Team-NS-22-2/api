package com.mju.insuranceCompany.service.insurance.domain;


import lombok.*;

import javax.persistence.Entity;
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
@Builder
@PrimaryKeyJoinColumn(name = "health_detail_id")
public class HealthDetail extends InsuranceDetail {

	private int targetAge;
	private boolean targetSex;
	private boolean riskCriterion;

}