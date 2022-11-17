package com.mju.insuranceCompany.service.insurance.domain;


import lombok.*;

import javax.persistence.Column;
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
@Builder
@ToString
@PrimaryKeyJoinColumn(name = "health_detail_id")
public class HealthDetail extends InsuranceDetail {

	private int targetAge;
	@Column(columnDefinition = "TINYINT", length=1)
	private boolean targetSex;
	@Column(columnDefinition = "TINYINT", length=1)
	private boolean riskCriterion;

}