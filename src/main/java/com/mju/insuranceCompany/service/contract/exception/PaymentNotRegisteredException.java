package com.mju.insuranceCompany.service.contract.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import com.mju.insuranceCompany.global.exception.MyException;

import static com.mju.insuranceCompany.service.contract.exception.ContractErrorCode.PAYMENT_NOT_REGISTERED;

public class PaymentNotRegisteredException extends MyException {
    public PaymentNotRegisteredException() {
        super(PAYMENT_NOT_REGISTERED);
    }
}
