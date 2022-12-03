package com.mju.insuranceCompany.service.customer.domain;


import com.mju.insuranceCompany.service.accident.domain.complain.Complain;
import com.mju.insuranceCompany.service.customer.controller.dto.CustomerDto;
import com.mju.insuranceCompany.service.customer.controller.dto.PaymentCreateDto;
import com.mju.insuranceCompany.service.customer.domain.payment.*;
import com.mju.insuranceCompany.service.customer.exception.PaymentListEmptyException;
import com.mju.insuranceCompany.service.customer.exception.PaymentNotFoundException;
import com.mju.outerSystem.FinancialInstitute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int id;
	private String name;
	private String ssn;
	private String address;
	private String phone;
	private String email;
	private String job;
	@OneToMany(mappedBy = "customerId",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Payment> paymentList = new ArrayList<>();
	@OneToMany
	private List<Complain> complainList;

	public Customer(CustomerDto dto) {
		name = dto.getName();
		ssn = dto.getSsn();
		address = dto.getAddress();
		phone = dto.getPhone();
		email = dto.getEmail();
		job = dto.getJob();
	}


	public List<Payment> readPayments(){
		if(this.paymentList.isEmpty()){
			throw new PaymentListEmptyException();
		}

		return this.paymentList;
	}
//
	private Payment createPayment(PaymentCreateDto paymentCreateDto) {
		PayType payType = paymentCreateDto.getPayType();
		Payment payment;
		if (payType.equals(PayType.CARD)) {
			payment = Card.builder()
					.cardNo(paymentCreateDto.getCardNo())
					.cvcNo(paymentCreateDto.getCvcNo())
					.cardType(paymentCreateDto.getCardType())
					.expiryDate(paymentCreateDto.getExpiryDate())
					.build();
		} else {
			payment = Account.builder()
					.accountNo(paymentCreateDto.getAccountNo())
					.bankType(paymentCreateDto.getBankType())
					.build();
		}
		payment.setPayType(payType);
		payment.setCustomerId(this.id);
		return payment;
	}
	// use-case: 결제 수단 추가
	public void addPayment(PaymentCreateDto paymentCreateDto){
		Payment payment = createPayment(paymentCreateDto);
		FinancialInstitute.validPaymentInfo(payment);
		this.paymentList.add(payment);
	}

	// use-case: 결제수단을 설정한다.
	// Contract에 따로 RegisterPayment를 만들고 여기선 반환만 해주면 될 듯.
	public Payment getPayment(int paymentId) {
		for (Payment payment : paymentList) {
			if(payment.getId() == paymentId)
				return payment;
		}
		throw new PaymentNotFoundException();
	}

	// use-case: 보험료를 납부한다. -> Contract의 Method로 이전
	/*

	// use-case: 보상처리 담당자를 변경한다.
	public Employee changeCompEmp(String reason, Employee compEmployee){
		Complain complain = Complain.builder().reason(reason)
				.customerId(this.id).build();

		this.complainList.add(complain);
		ComplainDao complainDao = new ComplainDaoImpl();
		complainDao.create(complain);

		return CompAssignUtil.changeCompEmployee(compEmployee);
	}

 */

}