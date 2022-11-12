package com.mju.insurancecompany.original.application.domain.accident;

import insuranceCompany.application.domain.accident.accidentDocumentFile.AccDocType;
import insuranceCompany.application.domain.accident.accidentDocumentFile.AccidentDocumentFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:22
 */
public abstract class Accident {

	protected Map<AccDocType, AccidentDocumentFile> accDocFileList = new HashMap<>();
	protected AccidentType accidentType;
	protected int customerId;
	protected LocalDateTime dateOfAccident;
	protected LocalDateTime dateOfReport;
	protected int employeeId;
	protected int id;
	protected long lossReserves; // 지급준비금




	public Accident(){

	}

	public Map<AccDocType, AccidentDocumentFile> getAccDocFileList() {
		return accDocFileList;
	}

	public Accident setAccDocFileList(Map<AccDocType, AccidentDocumentFile> accDocFileList) {
		this.accDocFileList = accDocFileList;
		return this;
	}

	public AccidentType getAccidentType() {
		return accidentType;
	}

	public Accident setAccidentType(AccidentType accidentType) {
		this.accidentType = accidentType;
		return this;
	}

	public int getCustomerId() {
		return customerId;
	}

	public Accident setCustomerId(int customerId) {
		this.customerId = customerId;
		return this;
	}

	public LocalDateTime getDateOfAccident() {
		return dateOfAccident;
	}

	public Accident setDateOfAccident(LocalDateTime dateOfAccident) {
		this.dateOfAccident = dateOfAccident;
		return this;
	}

	public LocalDateTime getDateOfReport() {
		return dateOfReport;
	}

	public Accident setDateOfReport(LocalDateTime dateOfReport) {
		this.dateOfReport = dateOfReport;
		return this;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public Accident setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	public int getId() {
		return id;
	}

	public Accident setId(int id) {
		this.id = id;
		return this;
	}

	public long getLossReserves() {
		return lossReserves;
	}

	public Accident setLossReserves(long lossReserves) {
		this.lossReserves = lossReserves;
		return this;
	}


	public abstract String toString();

	public abstract void printForCustomer();

	public void printForComEmployee() {
		String reportTime = dateOfReport.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));

		System.out.println("ID : " + this.id + " 사고 유형 : " + accidentType.name() + " 접수 일시 : " + reportTime);
	}
}