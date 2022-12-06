package com.mju.insuranceCompany.service.insurance.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class SalesAuthFileTypeBadRequestException extends MyException {
    public SalesAuthFileTypeBadRequestException() {
        super(InsuranceErrorCode.SALES_AUTH_FILE_TYPE_BAD_REQUEST);
    }
}
