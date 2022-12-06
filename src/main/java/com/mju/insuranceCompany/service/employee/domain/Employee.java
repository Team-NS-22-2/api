package com.mju.insuranceCompany.service.employee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author SeungHo
 * @version 1.0
 * @created 09-5-2022 오후 4:38:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private int id;
	private String name;
	private String phone;
	@Enumerated(value = EnumType.STRING)
	private Department department;
	@Enumerated(value = EnumType.STRING)
	private Position position;

	/*
	// 하위 메소드들은 -> Accident.class
	/*
	public AssessDamageResponseDto assessDamage(Accident accident, AccountRequestDto accountRequestDto){
		return AssessDamageResponseDto.builder().accidentDocumentFile(uploadLossAssessment(accident))
				.account(new Account().setBankType(accountRequestDto.getBankType()).setAccountNo(accountRequestDto.getAccountNo()))
				.build();
	}

	private AccidentDocumentFile uploadLossAssessment(Accident accident) {


		String dir = "./AccDocFile/submit/"+accident.getCustomerId()+"/"+accident.getId()+"/"+ AccDocType.LOSSASSESSMENT.getDesc()+".hwp";
		AccidentDocumentFile lossAssessment = uploadDocfile(accident, dir, AccDocType.LOSSASSESSMENT);

		createOrUpdateFile(accident, lossAssessment);
		return lossAssessment;
	}

	private void createOrUpdateFile(Accident accident, AccidentDocumentFile accidentDocFile) {
		boolean isExist = false;
		int lossId = 0;
		for (AccidentDocumentFile accidentDocumentFile : accident.getAccDocFileList().values()) {
			if (accidentDocumentFile.getType() == AccDocType.LOSSASSESSMENT) {
				lossId = accidentDocumentFile.getId();
				isExist = true;
			}
		}
		AccidentDocumentFileDao accidentDocumentFileDao = new AccidentDocumentFileDaoImpl();
		if (isExist) {
			accidentDocumentFileDao.update(lossId);
		} else {
			accidentDocumentFileDao.create(accidentDocFile);
		}
	}

	private AccidentDocumentFile uploadDocfile(Accident accident, String dir,AccDocType accDocType) {
		String fileDir = FileDialogUtil.uploadAccidentDocumentFile(dir);
		if (fileDir == null) {
			throw new MyInvalidAccessException(CompensationViewLogicConstants.ERROR_ASSESS_DAMAGE);
		}
		AccidentDocumentFile accidentDocumentFile = new AccidentDocumentFile();
		accidentDocumentFile.setFileAddress(fileDir)
				.setAccidentId(accident.getId())
				.setType(accDocType);
		accident.getAccDocFileList().put(accDocType, accidentDocumentFile);
		return accidentDocumentFile;
	}

	public void investigateDamage(InvestigateDamageRequestDto dto, Accident accident){
		if (!accident.getAccDocFileList().containsKey(AccDocType.INVESTIGATEACCIDENT)) {
			String dir = "./AccDocFile/submit/"+accident.getCustomerId()+"/"+accident.getId()+"/"+AccDocType.INVESTIGATEACCIDENT.getDesc()+".hwp";
			AccidentDocumentFile accidentDocumentFile = uploadDocfile(accident, dir, AccDocType.INVESTIGATEACCIDENT);
			createOrUpdateFile(accident,accidentDocumentFile);
		}


		accident.setLossReserves(dto.getLossReserves());
		if(accident.getAccidentType() == AccidentType.CARACCIDENT)
			((CarAccident)accident).setErrorRate(dto.getErrorRate());

		AccidentDao accidentDao = new AccidentDaoImpl();
		if(accident.getAccidentType() == AccidentType.CARACCIDENT)
			accidentDao.updateLossReserveAndErrorRate(accident);
		else
			accidentDao.updateLossReserve(accident);

	}

 */

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return getId() == employee.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

}