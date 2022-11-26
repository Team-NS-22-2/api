package com.mju.outerSystem;

import com.mju.insuranceCompany.global.utility.ConsoleColors;
import com.mju.insuranceCompany.service.customer.domain.payment.Payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface ElectronicPaymentSystem {
    void pay(Payment payment,int premium) ;
}
