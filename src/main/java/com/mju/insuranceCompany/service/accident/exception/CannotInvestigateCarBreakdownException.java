package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class CannotInvestigateCarBreakdownException extends MyException {
    public CannotInvestigateCarBreakdownException() {
        super(AccidentErrorCode.CANNOT_INVESTIGATE_CAR_BREAKDOWN);
    }
}
