package com.mju.insuranceCompany.application.domain.insurance;


import lombok.*;

import javax.persistence.Entity;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:38:57
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CarDetail extends InsuranceDetail {

	private int targetAge;
	private long valueCriterion;

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

	public String printValueCriterion() {
		String valueRange;
		if(valueCriterion == 150000000L) valueRange = "1억 5000만원 이상";
		else if(valueCriterion == 90000000L) valueRange = "9000만원 ~ 1억 5000만원";
		else if(valueCriterion == 70000000L) valueRange = "7000만원 ~ 9000만원";
		else if(valueCriterion == 50000000L) valueRange = "5000만원 ~ 7000만원";
		else if(valueCriterion == 30000000L) valueRange = "3000만원 ~ 5000만원";
		else if(valueCriterion == 10000000L) valueRange = "1000만원 ~ 3000만원";
		else valueRange = "1000만원 이하";
		return valueRange;
	}

}