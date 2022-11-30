package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class CannotReportFireAccidentException extends MyException {
    public CannotReportFireAccidentException() {
        super(AccidentErrorCode.CANNOT_REPORT_FIRE_ACCIDENT);
    }
}
