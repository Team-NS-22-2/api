package com.mju.insuranceCompany.service.customer.domain.payment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Card extends Payment {

	private String cardNo;
	private CardType cardType;
	private String cvcNo;
	private LocalDate expiryDate;

	public void add(){

	}

	public void delete(){

	}

	public void pay(){

	}

}