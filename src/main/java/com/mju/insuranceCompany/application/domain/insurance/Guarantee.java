package com.mju.insuranceCompany.application.domain.insurance;


import com.mju.insuranceCompany.application.viewlogic.dto.insurance.dto.GuaranteeDto;
import lombok.*;

import javax.persistence.*;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:39:00
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Guarantee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "guarantee_id")
	private int id;
	private String name;
	private String description;
	@Column(name = "amount")
	private Long guaranteeAmount;
	@ManyToOne
	@JoinColumn(name = "insurance_id")
	private Insurance insurance;

	public GuaranteeDto toGuaranteeDto() {
		return GuaranteeDto.builder()
				.name(this.getName())
				.description(this.getDescription())
				.amount(this.getGuaranteeAmount())
				.build();
	}

}