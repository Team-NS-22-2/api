package com.mju.insuranceCompany.service.insurance.domain;


import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:39:00
 */
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "health_detail_id")
@Accessors(chain = true)
public class HealthDetail extends InsuranceDetail {

	private int targetAge;
	private boolean targetSex;
	private boolean riskCriterion;

}