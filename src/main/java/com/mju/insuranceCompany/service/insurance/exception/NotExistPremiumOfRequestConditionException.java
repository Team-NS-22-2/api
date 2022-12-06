package com.mju.insuranceCompany.service.insurance.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class NotExistPremiumOfRequestConditionException extends MyException {
    public NotExistPremiumOfRequestConditionException() {
        super(InsuranceErrorCode.NOT_EXIST_PREMIUM_OF_REQUEST_CONDITION);
    }
}
