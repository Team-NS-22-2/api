package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class CannotClaimCarBreakdownException extends MyException {
    public CannotClaimCarBreakdownException() {
        super(AccidentErrorCode.CANNOT_CLAIM_CAR_BREAKDOWN);
    }
}
