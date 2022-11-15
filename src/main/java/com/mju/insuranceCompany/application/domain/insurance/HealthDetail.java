package com.mju.insuranceCompany.application.domain.insurance;


import com.mju.insuranceCompany.application.viewlogic.dto.customer.response.HealthDetailDto;
import lombok.*;

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
	private boolean targetSex;
	private boolean riskCriterion;

}