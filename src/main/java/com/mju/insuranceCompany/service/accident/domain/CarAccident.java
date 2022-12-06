package com.mju.insuranceCompany.service.accident.domain;


import com.mju.insuranceCompany.service.accident.controller.dto.AccidentReportDto;
import com.mju.insuranceCompany.service.accident.controller.dto.InvestigateAccidentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn
public class CarAccident extends Accident {

	private String carNo;
	private int errorRate; // 과실비율
	private boolean isRequestOnSite; // 현장 출동 요청
	private String opposingDriverPhone;
	private String placeAddress;

	public static Accident createAccident(AccidentReportDto dto) {
		return new CarAccident(
				dto.getCarNo(),
				0,
				dto.getIsRequestOnSite(),
				dto.getOpposingDriverPhone(),
				dto.getPlaceAddress()
		);
	}

	@Override
	public void investigate(InvestigateAccidentDto dto) {
		super.checkExistInvestigateAccidentFile();
		this.errorRate = dto.getErrorRate();
		this.lossReserves = dto.getLossReserves();
	}
}