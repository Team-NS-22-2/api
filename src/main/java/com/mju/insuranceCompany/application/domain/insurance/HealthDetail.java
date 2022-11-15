package com.mju.insuranceCompany.application.domain.insurance;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
public class HealthDetail extends InsuranceDetail {

	@Id
	@Column(name = "health_detail_id")
	private int id;
	private int targetAge;
	private boolean targetSex;
	private boolean riskCriterion;

	public String printTargetAge() {
		 String ageRange = switch (targetAge) {
			case 100 -> "100세 이상";
			case 80 -> "80 ~ 100세";
			case 60 -> "60 ~ 80세";
			case 50 -> "50 ~ 60세";
			case 40 -> "40 ~ 50세";
			case 30 -> "30 ~ 40세";
			case 20 -> "20 ~ 30세";
			default -> "20세 이하";
		 };
		 return ageRange;
	}

}