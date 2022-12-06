package com.mju.outerSystem;

import com.mju.insuranceCompany.service.customer.domain.payment.Payment;

public interface ElectronicPaymentSystem {
    void pay(Payment payment,int premium) ;
}
