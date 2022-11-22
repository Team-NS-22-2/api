package com.mju.insuranceCompany.service.employee.domain;

import com.mju.insuranceCompany.service.insurance.domain.DevelopInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
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
@ToString(exclude = "developInfoList")
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	private List<DevelopInfo> developInfoList;

/*
	public void develop(InsuranceType type, DtoBasicInfo basicInfo, ArrayList<DtoGuarantee> guaranteeInfoList, ArrayList<DtoTypeInfo> typeInfoList){
		Insurance insurance = Insurance.builder()
				.name(basicInfo.getName())
				.description(basicInfo.getDescription())
				.contractPeriod(basicInfo.getContractPeriod())
				.paymentPeriod(basicInfo.getPaymentPeriod())
				.guaranteeList(developGuarantee(guaranteeInfoList))
				.developInfo(developDevInfo())
				.salesAuthorizationFile(new SalesAuthorizationFile())
				.build();
		switch (type) {
			case HEALTH -> developHealth(insurance, typeInfoList);
			case CAR -> developCar(insurance, typeInfoList);
			case FIRE -> developFire(insurance, typeInfoList);
		}
//		new InsuranceDaoImpl().create(insurance);
	}

	private ArrayList<Guarantee> developGuarantee(ArrayList<DtoGuarantee> guaranteeInfoList) {
		ArrayList<Guarantee> guaranteeList = new ArrayList<>();
		for(int i=0; i<guaranteeInfoList.size(); i++) {
			guaranteeList.add(Guarantee.builder().name(guaranteeInfoList.get(i).getName())
					.description(guaranteeInfoList.get(i).getDescription())
					.guaranteeAmount(guaranteeInfoList.get(i).getGuaranteeAmount()).build()
			);
		}
		return guaranteeList;
	}

	private DevelopInfo developDevInfo() {
		return DevelopInfo.builder().employeeId(this.id)
				.developDate(LocalDate.now())
				.salesAuthorizationState(SalesAuthorizationState.WAIT)
				.build();
	}

	private Insurance developHealth(Insurance insurance, ArrayList<DtoTypeInfo> typeInfoList) {
		insurance.setInsuranceType(InsuranceType.HEALTH);
		ArrayList<InsuranceDetail> insuranceDetails = new ArrayList<>();
		for(DtoTypeInfo dtoTypeInfo : typeInfoList) {
			HealthDetail healthDetail = new HealthDetail();
			healthDetail.setTargetAge(((DtoHealth) dtoTypeInfo).getTargetAge())
					.setTargetSex(((DtoHealth) dtoTypeInfo).isTargetSex())
					.setRiskCriterion(((DtoHealth) dtoTypeInfo).getRiskCriterion())
					.setPremium(dtoTypeInfo.getPremium());
			insuranceDetails.add(healthDetail);
		}
		return insurance.setInsuranceDetailList(insuranceDetails);
	}

	private Insurance developCar(Insurance insurance, ArrayList<DtoTypeInfo> typeInfoList) {
		insurance.setInsuranceType(InsuranceType.CAR);
		ArrayList<InsuranceDetail> insuranceDetails = new ArrayList<>();
		for(DtoTypeInfo dtoTypeInfo : typeInfoList) {
			CarDetail carDetail = new CarDetail();
			carDetail.setTargetAge(((DtoCar) dtoTypeInfo).getTargetAge())
					.setValueCriterion(((DtoCar) dtoTypeInfo).getValueCriterion())
					.setPremium(dtoTypeInfo.getPremium());
			insuranceDetails.add(carDetail);
		}
		return insurance.setInsuranceDetailList(insuranceDetails);
	}

	private Insurance developFire(Insurance insurance, ArrayList<DtoTypeInfo> typeInfoList) {
		insurance.setInsuranceType(InsuranceType.FIRE);
		ArrayList<InsuranceDetail> insuranceDetails = new ArrayList<>();
		for(DtoTypeInfo dtoTypeInfo : typeInfoList) {
			FireDetail fireDetail = new FireDetail();
			fireDetail.setTargetBuildingType(((DtoFire) dtoTypeInfo).getBuildingType())
					.setCollateralAmountCriterion(((DtoFire) dtoTypeInfo).getCollateralAmount())
					.setPremium(dtoTypeInfo.getPremium());
			insuranceDetails.add(fireDetail);
		}
		return insurance.setInsuranceDetailList(insuranceDetails);
	}

	public int calcStandardPremium(long damageAmount, long countContract, long businessExpense, double profitMargin){
		if(damageAmount <=0 || countContract <=0 || businessExpense <=0 || profitMargin <= 0 || profitMargin>=100)
			throw new InputInvalidDataException();

		damageAmount *= 10000;
		businessExpense *= 10000;
		profitMargin /= 100;
		long purePremium = damageAmount / countContract;
		long riskCost = businessExpense / countContract;
		int stPremium = (int) ((purePremium + riskCost) / (1 - profitMargin));
		return stPremium;
	}

	public int calcSpecificPremium(int stPremium, DtoTypeInfo dtoTypeInfo) {
		if(dtoTypeInfo instanceof DtoHealth)
			return calcHealthPremium(stPremium, (DtoHealth) dtoTypeInfo);
		else if(dtoTypeInfo instanceof DtoCar)
			return calcCarPremium(stPremium, (DtoCar) dtoTypeInfo);
		else if(dtoTypeInfo instanceof DtoFire)
			return calcFirePremium(stPremium, (DtoFire) dtoTypeInfo);

		return -1;
	}

	private int calcHealthPremium(int stPremium, DtoHealth dtoHealth) {
		double weightRatio = 1.0;
		int targetAge = dtoHealth.getTargetAge();
		boolean targetSex = dtoHealth.isTargetSex();
		boolean riskCriterion = dtoHealth.getRiskCriterion();

		switch (targetAge) {
			case 100 -> weightRatio *= 1.34;
			case 80 -> weightRatio *= 1.3;
			case 60 -> weightRatio *= 1.26;
			case 50 -> weightRatio *= 1.22;
			case 40 -> weightRatio *= 1.18;
			case 30 -> weightRatio *= 1.14;
			case 20 -> weightRatio *= 1.1;
			default -> weightRatio *= 1.06;
		}

		weightRatio = (targetSex) ? weightRatio * 1.2 : weightRatio * 1.1;

		weightRatio = (riskCriterion) ? weightRatio * 1.4 : weightRatio;

		return (int) (stPremium * weightRatio);
	}

	private int calcCarPremium(int stPremium, DtoCar dtoCar) {
		double weightRatio = 1.0;
		int targetAge = dtoCar.getTargetAge();
		long valueCriterion = dtoCar.getValueCriterion();

		switch (targetAge) {
			case 100 -> weightRatio *= 1.4;
			case 80 -> weightRatio *= 1.35;
			case 60 -> weightRatio *= 1.2;
			case 50 -> weightRatio *= 1.15;
			case 40 -> weightRatio *= 1.15;
			case 30 -> weightRatio *= 1.2;
			case 20 -> weightRatio *= 1.35;
			default -> weightRatio *= 1.4;
		}

		if(valueCriterion == 150000000L) weightRatio *= 2.2;
		else if(valueCriterion == 90000000L) weightRatio *= 1.8;
		else if(valueCriterion == 70000000L) weightRatio *= 1.6;
		else if(valueCriterion == 50000000L) weightRatio *= 1.4;
		else if(valueCriterion == 30000000L) weightRatio *= 1.3;
		else if(valueCriterion == 10000000L) weightRatio *= 1.15;
		else weightRatio *= 1.05;

		return (int) (stPremium * weightRatio);
	}

	private int calcFirePremium(int stPremium, DtoFire dtoFire) {
		double weightRatio = 1.0;
		BuildingType targetBuildingType = dtoFire.getBuildingType();
		long collateralAmountCriterion = dtoFire.getCollateralAmount();

		switch (targetBuildingType){
			case COMMERCIAL -> weightRatio *= 1.3;
			case INDUSTRIAL -> weightRatio *= 1.25;
			case INSTITUTIONAL -> weightRatio *= 1.15;
			case RESIDENTIAL -> weightRatio *= 1.1;
		}

		if(collateralAmountCriterion == 5000000000L) weightRatio *= 1.1;
		else if(collateralAmountCriterion == 1000000000L) weightRatio *= 1.15;
		else if(collateralAmountCriterion == 500000000L) weightRatio *= 1.2;
		else if(collateralAmountCriterion == 100000000L) weightRatio *= 1.25;
		else weightRatio *= 1.3;

		return (int) (stPremium * weightRatio);
	}

	public void modifySalesAuthState(Insurance insurance, SalesAuthorizationState modify) {
		insurance.getDevelopInfo().setSalesAuthorizationState(modify);
		if(modify == SalesAuthorizationState.PERMISSION){
			insurance.getDevelopInfo().setSalesStartDate(LocalDate.now());
		}
		new InsuranceDaoImpl().updateBySalesAuthState(insurance);
	}

	private boolean checkSalesAuthState(Insurance insurance) {
		SalesAuthorizationFile salesAuthFile = insurance.getSalesAuthorizationFile();
		return (salesAuthFile.getProdDeclaration()!=null) && (salesAuthFile.getFssOfficialDoc()!=null)
				&& (salesAuthFile.getIsoVerification()!=null) && (salesAuthFile.getSrActuaryVerification()!=null);
	}

	public int registerAuthProdDeclaration(Insurance insurance) throws IOException {
		if(insurance.getSalesAuthorizationFile().getProdDeclaration()!=null) return 0;
		else return uploadProd(insurance);
	}

	public int registerAuthProdDeclaration(Insurance insurance, String nullValue) throws IOException {
		insurance.getSalesAuthorizationFile().setProdDeclaration(nullValue);
		return uploadProd(insurance);
	}

	private int uploadProd(Insurance insurance) throws IOException {
		String dirInsurance = insurance.getId() + ". " + insurance.getName();
		String savePath = FileDialogUtil.upload(dirInsurance);
		if(savePath == null) return -1;
		insurance.getSalesAuthorizationFile().setProdDeclaration(savePath)
				.setModifiedProd(LocalDateTime.now());
		new InsuranceDaoImpl().updateByProd(insurance);
		if(checkSalesAuthState(insurance)) return 2;
		return 1;
	}

	public int registerAuthSrActuaryVerification(Insurance insurance) throws IOException {
		if(insurance.getSalesAuthorizationFile().getSrActuaryVerification()!=null) return 0;
		else return uploadSrActuary(insurance);
	}

	public int registerAuthSrActuaryVerification(Insurance insurance, String nullValue) throws IOException {
		insurance.getSalesAuthorizationFile().setSrActuaryVerification(nullValue);
		return uploadSrActuary(insurance);
	}

	private int uploadSrActuary(Insurance insurance) throws IOException {
		String dirInsurance = insurance.getId() + ". " + insurance.getName();
		String savePath = FileDialogUtil.upload(dirInsurance);
		if(savePath == null) return -1;
		insurance.getSalesAuthorizationFile().setSrActuaryVerification(savePath)
				.setModifiedSrActuary(LocalDateTime.now());
		new InsuranceDaoImpl().updateBySrActuary(insurance);
		if(checkSalesAuthState(insurance)) return 2;
		return 1;
	}

	public int registerAuthIsoVerification(Insurance insurance) throws IOException {
		if(insurance.getSalesAuthorizationFile().getIsoVerification()!=null) return 0;
		else return uploadIso(insurance);
	}

	public int registerAuthIsoVerification(Insurance insurance, String nullValue) throws IOException {
		insurance.getSalesAuthorizationFile().setIsoVerification(nullValue);
		return uploadIso(insurance);
	}

	private int uploadIso(Insurance insurance) throws IOException {
		String dirInsurance = insurance.getId() + ". " + insurance.getName();
		String savePath = FileDialogUtil.upload(dirInsurance);
		if(savePath == null) return -1;
		insurance.getSalesAuthorizationFile().setIsoVerification(savePath)
				.setModifiedIso(LocalDateTime.now());
		new InsuranceDaoImpl().updateByIso(insurance);
		if(checkSalesAuthState(insurance)) return 2;
		return 1;
	}

	public int registerAuthFssOfficialDoc(Insurance insurance) throws IOException {
		if(insurance.getSalesAuthorizationFile().getFssOfficialDoc()!=null) return 0;
		else return uploadFss(insurance);
	}

	public int registerAuthFssOfficialDoc(Insurance insurance, String nullValue) throws IOException {
		insurance.getSalesAuthorizationFile().setFssOfficialDoc(nullValue);
		return uploadFss(insurance);
	}

	private int uploadFss(Insurance insurance) throws IOException {
		String dirInsurance = insurance.getId() + ". " + insurance.getName();
		String savePath = FileDialogUtil.upload(dirInsurance);
		if(savePath == null) return -1;
		insurance.getSalesAuthorizationFile().setFssOfficialDoc(savePath)
				.setModifiedFss(LocalDateTime.now());
		new InsuranceDaoImpl().updateByFss(insurance);
		if(checkSalesAuthState(insurance)) return 2;
		return 1;
	}
	public AssessDamageResponseDto assessDamage(Accident accident, AccountRequestDto accountRequestDto){
		return AssessDamageResponseDto.builder().accidentDocumentFile(uploadLossAssessment(accident))
				.account(new Account().setBankType(accountRequestDto.getBankType()).setAccountNo(accountRequestDto.getAccountNo()))
				.build();
	}

	private AccidentDocumentFile uploadLossAssessment(Accident accident) {


		String dir = "./AccDocFile/submit/"+accident.getCustomerId()+"/"+accident.getId()+"/"+AccDocType.LOSSASSESSMENT.getDesc()+".hwp";
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