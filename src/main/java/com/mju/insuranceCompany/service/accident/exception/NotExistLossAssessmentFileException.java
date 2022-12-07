package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class NotExistLossAssessmentFileException extends MyException  {
    public NotExistLossAssessmentFileException() {
        super(AccidentErrorCode.NOT_EXIST_LOSS_ASSESSMENT_FILE);
    }
}
