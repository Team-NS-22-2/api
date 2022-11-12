package com.mju.insuranceCompany.application.dao.customer;

import com.mju.insuranceCompany.application.dao.CrudInterface;
import com.mju.insuranceCompany.application.domain.customer.payment.Payment;

import java.util.List;

/**
 * packageName :  main.domain.payment
 * fileName : PaymentList
 * author :  규현
 * date : 2022-05-12
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-12                규현             최초 생성
 */
public interface PaymentDao extends CrudInterface<Payment> {

    List<Payment> findAllByCustomerId(int customerId);
}
