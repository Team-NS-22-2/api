package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class MismatchRequestClientAndAccidentException extends MyException {
    public MismatchRequestClientAndAccidentException() {
        super(AccidentErrorCode.MISMATCH_REQUEST_CLIENT_AND_ACCIDENT);
    }
}
