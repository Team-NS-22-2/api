package com.mju.insuranceCompany.service.insurance.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class SalesAuthStateInsufficientConditionException extends MyException {
    public SalesAuthStateInsufficientConditionException() {
        super(InsuranceErrorCode.INSUFFICIENT_CONDITIONS_SALES_AUTH_STATE);
    }
}
