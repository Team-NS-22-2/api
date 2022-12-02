package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class NotExistClientAccidentsException extends MyException {
    public NotExistClientAccidentsException() {
        super(AccidentErrorCode.NOT_EXIST_CLIENT_ACCIDENTS);
    }
}
