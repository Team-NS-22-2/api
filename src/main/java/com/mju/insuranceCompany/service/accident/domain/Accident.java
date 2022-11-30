package com.mju.insuranceCompany.service.accident.domain;

import com.mju.insuranceCompany.service.accident.controller.dto.AccidentReportDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Accessors(chain = true)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Accident {


//	protected Map<AccDocType, AccidentDocumentFile> accDocFileList = new HashMap<>();
	protected AccidentType accidentType;
	protected int customerId;
	protected LocalDateTime dateOfAccident;
	protected LocalDateTime dateOfReport;
	protected int employeeId;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accident_id")
	protected int id;
	protected long lossReserves; // 지급준비금

	public abstract boolean isRequestOnSite();

	public static Accident createAccident(AccidentType accidentType, int customerId, AccidentReportDto dto) {
		return switch (accidentType) {
			case CAR_ACCIDENT ->
				CarAccident.createAccident(dto)
						.setAccidentType(AccidentType.CAR_ACCIDENT)
						.createCommonAccident(customerId, dto);
			case CAR_BREAKDOWN ->
				CarBreakdown.createAccident(dto)
						.setAccidentType(AccidentType.CAR_BREAKDOWN)
						.createCommonAccident(customerId, dto);
			case FIRE_ACCIDENT ->
				FireAccident.createAccident(dto)
						.setAccidentType(AccidentType.FIRE_ACCIDENT)
						.createCommonAccident(customerId, dto);
			case INJURY_ACCIDENT ->
				 InjuryAccident.createAccident(dto)
						.setAccidentType(AccidentType.INJURY_ACCIDENT)
						.createCommonAccident(customerId, dto);
		};
	}

	private Accident createCommonAccident(int customerId, AccidentReportDto dto) {
		return this.setCustomerId(customerId)
				.setDateOfAccident(dto.getDateOfAccident())
				.setDateOfReport(LocalDateTime.now());
	}

}