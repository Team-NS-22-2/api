package com.mju.insuranceCompany.service.contract.exception;

import com.mju.insuranceCompany.global.exception.MyException;
import com.mju.insuranceCompany.service.insurance.exception.InsuranceErrorCode;

public class MismatchInsuranceTypeAndRequestContractException extends MyException {
    public MismatchInsuranceTypeAndRequestContractException() {
        super(InsuranceErrorCode.MISMATCH_INSURANCE_TYPE_AND_REQUEST_CONTRACT);
    }
}
