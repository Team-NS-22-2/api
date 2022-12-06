package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class OnSiteSystemResponseErrorException extends MyException {
    public OnSiteSystemResponseErrorException() {
        super(AccidentErrorCode.ON_SITE_SYSTEM_RESPONSE_ERROR);
    }
}
