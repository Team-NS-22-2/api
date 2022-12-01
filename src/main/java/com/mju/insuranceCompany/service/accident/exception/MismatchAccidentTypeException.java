package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class MismatchAccidentTypeException extends MyException {
    public MismatchAccidentTypeException() {
        super(AccidentErrorCode.MISMATCH_ACCIDENT_TYPE);
    }
}
