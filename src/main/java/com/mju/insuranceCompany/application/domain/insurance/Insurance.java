package com.mju.insuranceCompany.application.domain.insurance;

import com.mju.insuranceCompany.application.viewlogic.dto.insurance.response.InsuranceGuaranteeResponse;
import com.mju.insuranceCompany.application.viewlogic.dto.insurance.dto.InsuranceListDto;
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "insurance")
	private List<Guarantee> guaranteeList;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "insurance")
	private List<InsuranceDetail> insuranceDetailList;
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "insuranceId")
//	private DevelopInfo developInfo;
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "insuranceId")
//	private SalesAuthorizationFile salesAuthorizationFile;

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