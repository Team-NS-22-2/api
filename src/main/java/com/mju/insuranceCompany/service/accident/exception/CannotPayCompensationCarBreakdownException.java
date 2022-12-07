package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class CannotPayCompensationCarBreakdownException extends MyException {
    public CannotPayCompensationCarBreakdownException() {
        super(AccidentErrorCode.CANNOT_PAY_COMPENSATION_CAR_BREAKDOWN);
    }
}
