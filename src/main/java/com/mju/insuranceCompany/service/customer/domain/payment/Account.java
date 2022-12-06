package com.mju.insuranceCompany.service.customer.domain.payment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:22
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder @Getter
public class Account extends Payment {

	private String accountNo;

	@Enumerated(value = EnumType.STRING)
	private BankType bankType;

	@Override
	public String toString() {
		return "ID : " + id + " 종류 : 계좌 " + " 은행 : " + bankType.name() + " 계좌 번호 : " + accountNo;

	}

}