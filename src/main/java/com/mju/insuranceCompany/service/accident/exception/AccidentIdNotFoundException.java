package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class AccidentIdNotFoundException extends MyException {
    public AccidentIdNotFoundException() {
        super(AccidentErrorCode.ACCIDENT_ID_NOT_FOUND);
    }
}
