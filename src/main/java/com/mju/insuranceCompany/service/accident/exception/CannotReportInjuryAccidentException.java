package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class CannotReportInjuryAccidentException extends MyException {
    public CannotReportInjuryAccidentException() {
        super(AccidentErrorCode.CANNOT_REPORT_INJURY_ACCIDENT);
    }
}
