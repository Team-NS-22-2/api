package com.mju.insurancecompany.original.application.domain.insurance;

import java.util.ArrayList;

/**
 * @author SeungHo
 * @version 1.0
 * @created 09-5-2022 오후 4:39:01
 */
public class Insurance {

	private int id;
	private String name;
	private String description;
	private int contractPeriod;
	private int paymentPeriod;
	private InsuranceType insuranceType;
	private ArrayList<Guarantee> guaranteeList = new ArrayList<>();
	private ArrayList<InsuranceDetail> insuranceDetailList = new ArrayList<>();
	private DevelopInfo developInfo;
	private SalesAuthorizationFile salesAuthorizationFile;

	public Insurance(){
	}

	public int getId() {
		return id;
	}

	public Insurance setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Insurance setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Insurance setDescription(String description) {
		this.description = description;
		return this;
	}

	public int getContractPeriod() {
		return contractPeriod;
	}

	public Insurance setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
		return this;
	}

	public int getPaymentPeriod() {
		return paymentPeriod;
	}

	public Insurance setPaymentPeriod(int paymentPeriod) {
		this.paymentPeriod = paymentPeriod;
		return this;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public Insurance setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
		return this;
	}

	public ArrayList<Guarantee> getGuaranteeList() {
		return guaranteeList;
	}

	public Insurance setGuaranteeList(ArrayList<Guarantee> guaranteeList) {
		this.guaranteeList = guaranteeList;
		return this;
	}

	public ArrayList<InsuranceDetail> getInsuranceDetailList() {
		return insuranceDetailList;
	}

	public Insurance setInsuranceDetailList(ArrayList<InsuranceDetail> insuranceDetailList) {
		this.insuranceDetailList = insuranceDetailList;
		return this;
	}

	public DevelopInfo getDevelopInfo() {
		return developInfo;
	}

	public Insurance setDevelopInfo(DevelopInfo developInfo) {
		this.developInfo = developInfo;
		return this;
	}

	public SalesAuthorizationFile getSalesAuthorizationFile() {
		return salesAuthorizationFile;
	}

	public Insurance setSalesAuthorizationFile(SalesAuthorizationFile salesAuthorizationFile) {
		this.salesAuthorizationFile = salesAuthorizationFile;
		return this;
	}

	public String printInsuranceType() {
		String type = switch (insuranceType){
			case HEALTH -> "건강보험";
			case FIRE -> "화재보험";
			case CAR -> "자동차보험";
		};
		return type;
	}

	public String printGuarantee() {
		String value = "\n";
		for(Guarantee guarantee : guaranteeList) {
			value += "  보장정보: " + guarantee.print() + "\n";
		}
		return value;
	}

	private String printInsuranceDetail() {
		String value = "";
		for(InsuranceDetail insuranceDetail : insuranceDetailList) {
					value += "  " + insuranceDetail.print() + "\n";
		}
		return value;
	}

	public String print() {
		return "보험 정보 {\n" +
				"보험ID: " + id +
				", 보험유형: " + insuranceType.name() +
				", 이름: '" + name + '\'' +
				", 설명: '" + description + '\'' +
				", 계약기간: " + contractPeriod +
				", 납입기간: " + paymentPeriod +
				", 개발정보 " + developInfo.print() +
				", 인가파일 " + salesAuthorizationFile.print() +
				", " + printGuarantee() +
				printInsuranceDetail() +
				'}';
	}

	public String printOnlyInsurance() {
		return "보험 정보 { " +
				"보험ID: " + id +
				", 보험유형: " + insuranceType.name() +
				", 이름: '" + name + '\'' +
				", 설명: '" + description + '\'' +
				", 계약기간: " + contractPeriod +
				", 납입기간: " + paymentPeriod +
				", 개발정보 " + developInfo.print() +
				", 인가파일 " + salesAuthorizationFile.print() +
				", 보장정보 개수: " + (guaranteeList.size()-1) +
				", 보험상세정보 개수: " + (insuranceDetailList.size()-1) +
				"}";
	}

	public String[] getColumnName() {
		String[] column = {"보험ID", "보험유형", "이름", "설명", "계약기간", "납입기간"};
		return column;
	}

}