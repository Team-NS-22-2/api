package com.mju.insuranceCompany.service.insurance.exception;

import com.mju.insuranceCompany.global.exception.MyException;

import static com.mju.insuranceCompany.service.insurance.exception.InsuranceErrorCode.INSURANCE_ID_NOT_FOUND;

public class InsuranceIdNotFoundException extends MyException {
    public InsuranceIdNotFoundException() {
        super(INSURANCE_ID_NOT_FOUND);
    }
}
