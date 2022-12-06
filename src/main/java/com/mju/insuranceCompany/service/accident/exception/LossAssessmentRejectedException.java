package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import com.mju.insuranceCompany.global.exception.MyException;

import static com.mju.insuranceCompany.service.accident.exception.AccidentErrorCode.LOSS_ASSESSMENT_REJECTED;

public class LossAssessmentRejectedException extends MyException {
    public LossAssessmentRejectedException() {
        super(LOSS_ASSESSMENT_REJECTED);
    }
}
