package com.mju.insuranceCompany.application.domain.contract;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:24
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class HealthContract extends Contract{

	private int height;
	private int weight;
	private boolean isDrinking;
	private boolean isSmoking;
	private boolean isDriving;
	private boolean isDangerActivity;
	private boolean isHavingDisease;
	private boolean isTakingDrug;
	private String diseaseDetail;

}