package com.mju.insuranceCompany.service.accident.domain;

import com.mju.insuranceCompany.service.accident.controller.dto.AccidentReportDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:23
 */
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn
public class CarBreakdown extends Accident {

	private String carNo;
	private String placeAddress;
	private String symptom;

	public static Accident createAccident(AccidentReportDto dto) {
		return new CarBreakdown(
				dto.getCarNo(),
				dto.getPlaceAddress(),
				dto.getSymptom()
		);
	}

	@Override
	public boolean isRequestOnSite() {
		return true;
	}
}