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
public class InjuryAccident extends Accident {

	private String injurySite; // 부상 부위

	public static Accident createAccident(AccidentReportDto dto) {
		return new InjuryAccident(dto.getInjurySite());
	}

	@Override
	public boolean isRequestOnSite() {
		return false;
	}

	@Override
	public void investigate(InvestigateAccidentDto dto) {
		super.checkExistInvestigateAccidentFile();
		this.lossReserves = dto.getLossReserves();
	}

}