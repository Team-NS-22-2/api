package com.mju.insuranceCompany.application.domain.customer.payment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public abstract class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	protected PayType paytype;
	protected int customerId;

}