package com.mju.insuranceCompany.service.contract.domain;


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
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Accessors(chain = true)
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_id")
	private int id;
	@Column(columnDefinition = "TINYINT", length=1)
	private boolean isPublishStock;
	private int paymentId;
	private int premium;
	private String reasonOfUw;
	private int insuranceId;
	@Enumerated(value = EnumType.STRING)
	private ConditionOfUw conditionOfUw;
	private int customerId;
	private int employeeId;

	protected Contract(boolean isPublishStock, int premium,  int insuranceId, ConditionOfUw conditionOfUw, int customerId, int employeeId) {
		this.isPublishStock = isPublishStock;
		this.premium = premium;
		this.insuranceId = insuranceId;
		this.conditionOfUw = conditionOfUw;
		this.customerId = customerId;
		this.employeeId = employeeId;
	}

	public void underwriting(String reasonOfUw, ConditionOfUw conditionOfUw){
		this.setReasonOfUw(reasonOfUw);
		this.setConditionOfUw(conditionOfUw);

		if (!conditionOfUw.getName().equals(ConditionOfUw.RE_AUDIT.getName())) this.setPublishStock(true);
	}
}