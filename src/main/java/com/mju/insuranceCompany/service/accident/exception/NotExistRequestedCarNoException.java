package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class NotExistRequestedCarNoException extends MyException {
    public NotExistRequestedCarNoException() {
        super(AccidentErrorCode.NOT_EXIST_REQUESTED_CAR_NO);
    }
}
