package com.mju.insuranceCompany.application.domain.contract;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Accessors(chain = true)
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_id")
	private int id;
	private boolean isPublishStock;
	private int paymentId;
	private int premium;
	private String reasonOfUw;
	private int insuranceId;
	private ConditionOfUw conditionOfUw;
	private int customerId;
	private int employeeId;


}