package com.mju.insuranceCompany.service.insurance.domain;


/**
 * @author SeungHo
 * @version 1.0
 * @created 09-5-2022 오전 2:42:24
 */
public enum InsuranceType {
	HEALTH("건강보험"),
	FIRE("화재보험"),
	CAR("자동차보험");
	private String name;
	InsuranceType(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}