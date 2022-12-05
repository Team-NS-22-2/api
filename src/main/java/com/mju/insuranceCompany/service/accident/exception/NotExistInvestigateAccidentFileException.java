package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class NotExistInvestigateAccidentFileException extends MyException {
    public NotExistInvestigateAccidentFileException() {
        super(AccidentErrorCode.NOT_EXIST_INVESTIGATE_ACCIDENT_FILE);
    }
}
