package com.mju.insuranceCompany.service.customer.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import com.mju.insuranceCompany.global.exception.MyException;

import static com.mju.insuranceCompany.service.customer.exception.CustomerErrorCode.PAYMENT_NOT_FOUND;

public class PaymentNotFoundException extends MyException {
    public PaymentNotFoundException() {
        super(PAYMENT_NOT_FOUND);
    }
}
