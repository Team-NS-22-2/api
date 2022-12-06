package com.mju.insuranceCompany.service.insurance.domain;


import lombok.*;

import javax.persistence.*;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:39:00
 */
@Getter @Setter
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "insurance_id")
	private Insurance insurance;

}