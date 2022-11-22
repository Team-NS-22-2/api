package com.mju.insuranceCompany.service.insurance.domain;

import com.mju.insuranceCompany.global.utility.CriterionSetUtil;
import com.mju.insuranceCompany.global.utility.TargetInfoCalculator;
import com.mju.insuranceCompany.service.contract.domain.BuildingType;
import com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceGuaranteeResponse;
import com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceListDto;
import lombok.*;

import javax.persistence.*;
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "insurance")
	private List<Guarantee> guaranteeList;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "insurance")
	private List<InsuranceDetail> insuranceDetailList;
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private DevelopInfo developInfo;
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private SalesAuthorizationFile salesAuthorizationFile;

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


	public InsuranceListDto toInsuranceListDto() {
		return InsuranceListDto.builder()
				.id(this.getId())
				.name(this.getName())
				.type(this.getInsuranceType())
				.build();
	}

	public InsuranceGuaranteeResponse toInsuranceGuaranteeDto() {
		return InsuranceGuaranteeResponse.builder()
				.name(this.getName())
				.type(this.getInsuranceType())
				.contractPeriod(this.getContractPeriod())
				.paymentPeriod(this.getPaymentPeriod())
				.guaranteeList(this.getGuaranteeList().stream().map(Guarantee::toGuaranteeDto).toList())
				.build();
	}

}