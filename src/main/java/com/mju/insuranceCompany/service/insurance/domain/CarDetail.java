package com.mju.insuranceCompany.service.insurance.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:38:57
 */
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@PrimaryKeyJoinColumn(name = "car_detail_id")
public class CarDetail extends InsuranceDetail {

	private int targetAge;
	private long valueCriterion;

}