package com.mju.insuranceCompany.service.insurance.domain;


import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:38:57
 */
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "car_detail_id")
@Accessors(chain = true)
public class CarDetail extends InsuranceDetail {

	private int targetAge;
	private long valueCriterion;

}