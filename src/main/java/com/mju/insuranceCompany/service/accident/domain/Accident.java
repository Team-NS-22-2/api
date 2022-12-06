package com.mju.insuranceCompany.service.accident.domain;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.accident.controller.dto.AccidentReportDto;
import com.mju.insuranceCompany.service.accident.controller.dto.InvestigateAccidentDto;
import com.mju.insuranceCompany.service.accident.controller.dto.PaymentOfCompensationDto;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccidentDocumentFile;
import com.mju.insuranceCompany.service.accident.exception.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accident_id")
	protected int id;
	@Enumerated(value = EnumType.STRING)
	protected AccidentType accidentType;
	protected int employeeId;
	protected int customerId;
	protected long lossReserves; // 지급준비금
	protected LocalDateTime dateOfAccident;
	protected LocalDateTime dateOfReport;
	@OneToMany(mappedBy = "accidentId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected List<AccidentDocumentFile> accidentDocumentFileList;

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

	public void addAccidentDocumentFile(AccDocType docType, String fileUrl) {
		if(this.accidentDocumentFileList == null) {
			this.setAccidentDocumentFileList(new ArrayList<>());
		}
		this.accidentDocumentFileList.add(AccidentDocumentFile.createAccidentDocumentFile(docType, fileUrl, this.id));
	}

	public void assignEmployeeId(int employeeId) {
		this.setEmployeeId(employeeId);
	}

	public boolean checkConditionForClaimCompensation(AccidentType accidentType) {
		List<AccDocType> conditions;
		switch (accidentType) {
			case CAR_ACCIDENT ->
				conditions = List.of(
						AccDocType.CLAIM_COMP,
						AccDocType.MEDICAL_CERTIFICATION,
						AccDocType.CONFIRM_ADMISSION_DISCHARGE,
						AccDocType.PICTURE_OF_SITE,
						AccDocType.CAR_ACCIDENT_FACT_CONFIRMATION,
						AccDocType.PAYMENT_RESOLUTION
				);
			case FIRE_ACCIDENT ->
				conditions = List.of(
						AccDocType.CLAIM_COMP,
						AccDocType.PICTURE_OF_SITE,
						AccDocType.REPAIR_ESTIMATE,
						AccDocType.REPAIR_RECEIPT
				);
			case INJURY_ACCIDENT ->
				conditions = List.of(
						AccDocType.CLAIM_COMP,
						AccDocType.MEDICAL_CERTIFICATION,
						AccDocType.CONFIRM_ADMISSION_DISCHARGE
				);
			default -> throw new CannotClaimCarBreakdownException();
		}

		List<AccDocType> accDocTypesOfCurrentAccDocFileList = this.accidentDocumentFileList.stream().map(AccidentDocumentFile::getType).toList();
		for(AccDocType condition : conditions) {
			if(!accDocTypesOfCurrentAccDocFileList.contains(condition)) {
				return false;
			}
		}
		return true;
	}

	public abstract void investigate(InvestigateAccidentDto dto);

	protected void checkExistInvestigateAccidentFile() {
		for(AccidentDocumentFile file : this.accidentDocumentFileList) {
			if(file.getType() == AccDocType.INVESTIGATE_ACCIDENT) {
				return;
			}
		}
		throw new NotExistInvestigateAccidentFileException();
	}

	public String checkForPayCompensation(PaymentOfCompensationDto dto) {
		if(this.lossReserves * 1.5 < dto.getAmount()) {
			return "손해사정서가 반려되었습니다.";
		}
		if(this instanceof CarAccident c && c.getErrorRate() == 0) {
			return "고객 과실이 0이기 때문에 보상금을 지급하지 않습니다.";
		}
		return "";
	}

	/** 사고의 고객 ID와 요청 고객 ID를 검증. */
	public void validateClient() {
		int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
		if(this.getCustomerId() != customerId) {
			throw new MismatchRequestClientAndAccidentException();
		}
	}

	/** 사고의 할당 직원 ID과 로그인 보상팀 직원 ID를 검증. */
	public void validateCompEmployee() {
		int compEmployeeId = AuthenticationExtractor.extractEmployeeIdByAuthentication();
		if(this.employeeId != compEmployeeId) {
			throw new MismatchRequestEmployeeAndAccidentException();
		}
	}

	/** 사고의 사고 타입과 요청 사고의 타입을 검증. */
	public void validateAccidentType(AccidentType requestAccidentType) {
		if(this.accidentType != requestAccidentType) {
			throw new MismatchAccidentTypeException();
		}
	}

}