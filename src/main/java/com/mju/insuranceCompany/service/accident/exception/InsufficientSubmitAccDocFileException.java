package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class InsufficientSubmitAccDocFileException extends MyException {
    public InsufficientSubmitAccDocFileException() {
        super(AccidentErrorCode.INSUFFICIENT_SUBMIT_ACC_DOC_FILE);
    }
}
