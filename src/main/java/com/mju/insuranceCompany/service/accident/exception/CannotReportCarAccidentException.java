package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class CannotReportCarAccidentException extends MyException {
    public CannotReportCarAccidentException() {
        super(AccidentErrorCode.CANNOT_REPORT_CAR_ACCIDENT);
    }
}
