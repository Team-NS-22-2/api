package com.mju.insuranceCompany.service.insurance.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class StandardPremiumConditionException extends MyException {
    public StandardPremiumConditionException() {
        super(InsuranceErrorCode.STANDARD_PREMIUM_CONDITION_BAD_REQUEST);
    }
}
