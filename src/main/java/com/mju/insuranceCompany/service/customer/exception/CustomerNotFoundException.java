package com.mju.insuranceCompany.service.customer.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import com.mju.insuranceCompany.global.exception.MyException;

import static com.mju.insuranceCompany.service.customer.exception.CustomerErrorCode.CUSTOMER_NOT_FOUND;

public class CustomerNotFoundException extends MyException {
    public CustomerNotFoundException() {
        super(CUSTOMER_NOT_FOUND);
    }
}
