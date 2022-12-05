package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class MismatchRequestEmployeeAndAccidentException extends MyException {
    public MismatchRequestEmployeeAndAccidentException() {
        super(AccidentErrorCode.MISMATCH_REQUEST_EMPLOYEE_AND_ACCIDENT);
    }
}
