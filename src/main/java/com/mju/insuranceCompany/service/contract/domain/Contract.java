package com.mju.insuranceCompany.service.contract.domain;


import com.mju.insuranceCompany.service.contract.exception.PaymentNotRegisteredException;
import com.mju.insuranceCompany.service.customer.domain.payment.Payment;
import com.mju.outerSystem.ElectronicPaymentSystem;
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
	protected int id;
	protected boolean isPublishStock;
	protected int premium;
	protected String reasonOfUw;
	@Enumerated(value = EnumType.STRING)
	protected ConditionOfUw conditionOfUw;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	protected Payment payment;
	protected int insuranceId;
	protected int customerId;
	protected int employeeId;

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

	public void registerPayment(Payment payment) {
		this.payment = payment;
	}

	public void payPremium(ElectronicPaymentSystem electronicPaymentSystem){
		if(payment == null)
			throw new PaymentNotRegisteredException();
		electronicPaymentSystem.pay(payment,premium);
	}
}