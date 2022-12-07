package com.mju.insuranceCompany.service.accident.domain;


import com.mju.insuranceCompany.service.accident.controller.dto.AccidentReportDto;
import com.mju.insuranceCompany.service.accident.controller.dto.InvestigateAccidentDto;
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
public class FireAccident extends Accident {

	private String placeAddress;

	public static Accident createAccident(AccidentReportDto dto) {
		return new FireAccident(dto.getPlaceAddress());
	}

	@Override
	public boolean isRequestOnSite() {
		return false;
	}

	@Override
	public void investigate(InvestigateAccidentDto dto) {
		super.checkExistInvestigateAccidentFile();
		this.compState = CompState.ASSESSMENT;
		this.lossReserves = dto.getLossReserves();
	}

}