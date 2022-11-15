package com.mju.insuranceCompany.application.domain.insurance;

import com.mju.insuranceCompany.application.viewlogic.dto.customer.response.InsuranceCarDetailDto;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.response.InsuranceFireDetailDto;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.response.InsuranceHealthDetailDto;
import com.mju.insuranceCompany.application.viewlogic.dto.insurance.response.InsuranceListDto;
import com.mju.insuranceCompany.application.viewlogic.dto.insurance.response.InsuranceGuaranteeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	@OneToMany(mappedBy = "insurance")
	private List<Guarantee> guaranteeList;
	@OneToMany(mappedBy = "insurance")
	private List<InsuranceDetail> insuranceDetailList;

//	private DevelopInfo developInfo;
//	@OneToOne
//	private SalesAuthorizationFile salesAuthorizationFile;

	public InsuranceListDto toInsuranceListDto() {
		return InsuranceListDto.builder()
				.id(this.getId())
				.name(this.getName())
				.type(this.getInsuranceType())
				.build();
	}

	public InsuranceHealthDetailDto toInsuranceHealthDetailDto() {
		return InsuranceHealthDetailDto.builder()
				.id(this.getId())
				.insuranceType(this.getInsuranceType())
				.insuranceDetailList(this.getInsuranceDetailList().stream().map(e->(HealthDetail)e).map(HealthDetail::toDto).toList())
				.build();
	}

	public InsuranceFireDetailDto toInsuranceFireDetailDto() {
		return InsuranceFireDetailDto.builder()
				.id(this.getId())
				.insuranceType(this.getInsuranceType())
				.insuranceDetailList(this.getInsuranceDetailList().stream().map(e->(FireDetail)e).map(FireDetail::toDto).toList())
				.build();
	}

	public InsuranceCarDetailDto toInsuranceCarDetailDto() {
		return InsuranceCarDetailDto.builder()
				.id(this.getId())
				.insuranceType(this.getInsuranceType())
				.insuranceDetailList(this.getInsuranceDetailList().stream().map(e->(CarDetail)e).map(CarDetail::toDto).toList())
				.build();
	}

	public InsuranceGuaranteeDto toInsuranceGuaranteeDto() {
		return InsuranceGuaranteeDto.builder()
				.name(this.getName())
				.type(this.getInsuranceType())
				.contractPeriod(this.getContractPeriod())
				.paymentPeriod(this.getPaymentPeriod())
				.guaranteeList(this.getGuaranteeList().stream().map(Guarantee::toGuaranteeDto).toList())
				.build();
	}

}