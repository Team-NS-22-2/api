package com.mju.insuranceCompany.service.customer.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import com.mju.insuranceCompany.global.exception.MyException;

import static com.mju.insuranceCompany.service.customer.exception.CustomerErrorCode.PAYMENT_LIST_EMPTY;

public class PaymentListEmptyException extends MyException {
    public PaymentListEmptyException() {
        super(PAYMENT_LIST_EMPTY);
    }
}
