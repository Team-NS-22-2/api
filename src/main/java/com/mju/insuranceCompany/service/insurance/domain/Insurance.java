package com.mju.insuranceCompany.service.insurance.domain;

import com.mju.insuranceCompany.global.exception.InputInvalidDataException;
import com.mju.insuranceCompany.global.exception.NoResultantException;
import com.mju.insuranceCompany.global.utility.CriterionSetUtil;
import com.mju.insuranceCompany.global.utility.TargetInfoCalculator;
import com.mju.insuranceCompany.service.contract.domain.BuildingType;
import com.mju.insuranceCompany.service.insurance.controller.dto.*;
import com.mju.insuranceCompany.service.insurance.exception.SalesAuthStateInsufficientConditionException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SeungHo
 * @version 1.0
 * @created 09-5-2022 오후 4:39:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString(exclude = {"developInfo", "salesAuthorizationFile"})
public class Insurance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "insurance_id", unique = true, nullable = false)
	private int id;
	private String name;
	private String description;
	private int contractPeriod;
	private int paymentPeriod;
	@Enumerated(value = EnumType.STRING)
	private InsuranceType insuranceType;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "insurance", cascade = CascadeType.ALL)
	private List<Guarantee> guaranteeList;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "insurance", cascade = CascadeType.ALL)
	private List<InsuranceDetail> insuranceDetailList;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "insurance", cascade = CascadeType.ALL)
	private DevelopInfo developInfo;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "insurance", cascade = CascadeType.ALL)
	private SalesAuthorizationFile salesAuthorizationFile;


	//////////////////////////////////////////////////////////////////////////////////////////////////////
	private static Insurance createInsurance(InsuranceBasicInfoDto basicInfo,
											 List<GuaranteeDto> guaranteeInfoList,
										 	 int employeeId) {
		Insurance insurance = Insurance.builder()
				.name(basicInfo.getName())
				.description(basicInfo.getDescription())
				.contractPeriod(basicInfo.getContractPeriod())
				.paymentPeriod(basicInfo.getPaymentPeriod())
				.guaranteeList(createGuaranteeList(guaranteeInfoList))
				.developInfo(createDevelopInfo(employeeId))
				.salesAuthorizationFile(new SalesAuthorizationFile())
				.build();
		insurance.getDevelopInfo().setInsurance(insurance);
		insurance.getSalesAuthorizationFile().setInsurance(insurance);
		return insurance;
	}

	public static Insurance createHealthInsurance(InsuranceBasicInfoDto basicInfo,
												  List<GuaranteeDto> guaranteeInfoList,
												  int employeeId,
												  List<HealthDetailDto> healthDtoList){
		Insurance insurance = createInsurance(basicInfo, guaranteeInfoList, employeeId);
		insurance.setInsuranceType(InsuranceType.HEALTH);
		List<InsuranceDetail> insuranceDetails = new ArrayList<>();
		for(HealthDetailDto healthDetailDto: healthDtoList) {
			insuranceDetails.add(healthDetailDto.toEntity());
		}
		insurance.setInsuranceDetailList(insuranceDetails);
		return insurance;
	}

	public static Insurance createCarInsurance( InsuranceBasicInfoDto basicInfo,
												List<GuaranteeDto> guaranteeInfoList,
												int employeeId,
												List<CarDetailDto> carDtoList){
		Insurance insurance = createInsurance(basicInfo, guaranteeInfoList, employeeId);
		insurance.setInsuranceType(InsuranceType.CAR);
		List<InsuranceDetail> insuranceDetails = new ArrayList<>();
		for(CarDetailDto carDetailDto : carDtoList) {
			insuranceDetails.add(carDetailDto.toEntity());
		}
		insurance.setInsuranceDetailList(insuranceDetails);
		return insurance;
	}
	public static Insurance createFireInsurance(InsuranceBasicInfoDto basicInfo,
												List<GuaranteeDto> guaranteeInfoList,
												int employeeId,
												List<FireDetailDto> fireDtoList){
		Insurance insurance = createInsurance(basicInfo, guaranteeInfoList, employeeId);
		insurance.setInsuranceType(InsuranceType.FIRE);
		List<InsuranceDetail> insuranceDetails = new ArrayList<>();
		for(FireDetailDto fireDetailDto : fireDtoList) {
			insuranceDetails.add(fireDetailDto.toEntity());
		}
		insurance.setInsuranceDetailList(insuranceDetails);
		return insurance;
	}

	private static List<Guarantee> createGuaranteeList(List<GuaranteeDto> guaranteeInfoList) {
		List<Guarantee> guaranteeList = new ArrayList<>();
		for (GuaranteeDto guaranteeDto : guaranteeInfoList) {
			guaranteeList.add(Guarantee.builder()
					.name(guaranteeDto.getName())
					.description(guaranteeDto.getDescription())
					.guaranteeAmount(guaranteeDto.getAmount())
					.build()
			);
		}
		return guaranteeList;
	}

	private static DevelopInfo createDevelopInfo(int employeeId) {
		return DevelopInfo.builder()
				.employeeId(employeeId)
				.developDate(LocalDate.now())
				.salesAuthorizationState(SalesAuthorizationState.WAIT)
				.build();
	}

	private static void validatePremiumConditionForCalculate(StandardPremiumDto standardPremiumDto){
		if(standardPremiumDto.getDamageAmount() <=0 ||
				standardPremiumDto.getCountContract() <=0 ||
				standardPremiumDto.getBusinessExpense() <=0 ||
				standardPremiumDto.getProfitMargin() <= 0 ||
				standardPremiumDto.getProfitMargin()>=100)
			throw new InputInvalidDataException();
	}

	private static int calcStandardPremium(StandardPremiumDto standardPremiumDto){
		validatePremiumConditionForCalculate(standardPremiumDto);

		long damageAmount = standardPremiumDto.getDamageAmount() * 10000;
		long businessExpense = standardPremiumDto.getBusinessExpense() * 10000;
		double profitMargin = standardPremiumDto.getProfitMargin() / 100;
		long purePremium = damageAmount / standardPremiumDto.getCountContract();
		long riskCost = businessExpense / standardPremiumDto.getCountContract();
		return (int) ((purePremium + riskCost) / (1 - profitMargin));
	}

	public static int calcHealthPremium(StandardPremiumDto standardPremiumDto, HealthDetailDto dtoHealth) {
		int stPremium = calcStandardPremium(standardPremiumDto);
		double weightRatio = 1.0;
		int targetAge = dtoHealth.getTargetAge();
		boolean targetSex = dtoHealth.getTargetSex();
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

	public static int calcCarPremium(StandardPremiumDto standardPremiumDto, CarDetailDto dtoCar) {
		int stPremium = calcStandardPremium(standardPremiumDto);
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

	public static int calcFirePremium(StandardPremiumDto standardPremiumDto, FireDetailDto dtoFire) {
		int stPremium = calcStandardPremium(standardPremiumDto);
		double weightRatio = 1.0;
		BuildingType targetBuildingType = dtoFire.getTargetBuildingType();
		long collateralAmountCriterion = dtoFire.getCollateralAmountCriterion();

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

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	public int inquireHealthPremium(String ssn, int riskCount){
		int premium = 0;
		int targetAge = CriterionSetUtil.setTargetAge((TargetInfoCalculator.targetAgeCalculator(ssn)));
		boolean targetSex = TargetInfoCalculator.targetSexCalculator(ssn);
		boolean riskCriterion = CriterionSetUtil.setRiskCriterion(riskCount);

		for (InsuranceDetail insuranceDetail : this.insuranceDetailList) {
			HealthDetail healthDetail = (HealthDetail) insuranceDetail;
			if (healthDetail.getTargetAge() == targetAge && healthDetail.isTargetSex() == targetSex && (healthDetail.isRiskCriterion()) == riskCriterion) {
				premium = healthDetail.getPremium();
				break;
			}
		}
		if (premium == 0)
			throw new NoResultantException();
		return premium;
	}

	public int inquireFirePremium(BuildingType buildingType, Long collateralAmount){
		int premium = 0;
		Long collateralAmountCriterion = CriterionSetUtil.setCollateralAmountCriterion(collateralAmount);

		for (InsuranceDetail insuranceDetail : this.insuranceDetailList) {
			FireDetail fireDetail = (FireDetail) insuranceDetail;
			if (fireDetail.getTargetBuildingType() == buildingType && fireDetail.getCollateralAmountCriterion() == collateralAmountCriterion) {
				premium = fireDetail.getPremium();
				break;
			}
		}
		if (premium == 0)
			throw new NoResultantException();
		return premium;
	}

	public int inquireCarPremium(String ssn, Long value){
		int premium = 0;
		int targetAge = CriterionSetUtil.setTargetAge(TargetInfoCalculator.targetAgeCalculator(ssn));
		Long valueCriterion = CriterionSetUtil.setValueCriterion(value);

		for (InsuranceDetail insuranceDetail : this.insuranceDetailList) {
			CarDetail carDetail = (CarDetail) insuranceDetail;
			if (carDetail.getTargetAge() == targetAge && carDetail.getValueCriterion() == valueCriterion) {
				premium = carDetail.getPremium();
				break;
			}
		}
		if (premium == 0)
			throw new NoResultantException();
		return premium;
	}

	public UploadAuthFileResultDto uploadSalesAuthFile(SalesAuthFileType fileType, String fileUrl) {
		return switch (fileType) {
			case PROD -> getUploadFileResultDto(salesAuthorizationFile.setProdDeclaration(fileUrl));
			case ISO -> getUploadFileResultDto(salesAuthorizationFile.setIsoVerification(fileUrl));
			case SR_ACTUARY -> getUploadFileResultDto(salesAuthorizationFile.setSrActuaryVerification(fileUrl));
			case FSS_OFFICIAL -> getUploadFileResultDto(salesAuthorizationFile.setFssOfficialDoc(fileUrl));
		};
	}

	public String getOriginSalesAuthorizationFileUrl(SalesAuthFileType fileType) {
		return switch (fileType) {
			case PROD -> this.salesAuthorizationFile.getProdDeclaration();
			case ISO -> this.salesAuthorizationFile.getIsoVerification();
			case SR_ACTUARY -> this.salesAuthorizationFile.getSrActuaryVerification();
			case FSS_OFFICIAL -> this.salesAuthorizationFile.getFssOfficialDoc();
		};
	}

	public String deleteSalesAuthFile(SalesAuthFileType fileType) {
		this.developInfo.setSalesAuthorizationState(SalesAuthorizationState.DISALLOWANCE);
		return switch (fileType) {
			case PROD -> this.salesAuthorizationFile.deleteProdDeclaration();
			case ISO -> this.salesAuthorizationFile.deleteIsoVerification();
			case SR_ACTUARY -> this.salesAuthorizationFile.deleteSrActuaryVerification();
			case FSS_OFFICIAL -> this.salesAuthorizationFile.deleteFssOfficialDoc();
		};
	}

	public void updateSalesAuthorizationState(SalesAuthorizationState updatedState) {
		if(updatedState==SalesAuthorizationState.PERMISSION && !this.salesAuthorizationFile.isExistAllFile()) {
			throw new SalesAuthStateInsufficientConditionException();
		}
		this.developInfo.setSalesAuthorizationState(updatedState);
	}

	private UploadAuthFileResultDto getUploadFileResultDto(SalesAuthorizationFile salesAuthorizationFile) {
		return UploadAuthFileResultDto.builder()
				.isExistAllFile(salesAuthorizationFile.isExistAllFile()).build();
	}

}