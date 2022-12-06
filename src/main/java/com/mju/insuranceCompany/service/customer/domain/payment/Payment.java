package com.mju.insuranceCompany.service.customer.domain.payment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Payment {

	@Id @Column(name = "payment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;

	@Enumerated(value = EnumType.STRING)
	protected PayType payType;
	@JoinColumn(name = "customer_id")
	protected int customerId;

}