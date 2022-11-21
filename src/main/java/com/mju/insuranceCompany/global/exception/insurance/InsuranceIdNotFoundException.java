package com.mju.insuranceCompany.global.exception.insurance;

import com.mju.insuranceCompany.global.exception.MyException;
import org.springframework.http.HttpStatus;

import static com.mju.insuranceCompany.global.exception.insurance.InsuranceErrorCode.INSURANCE_ID_NOT_FOUND;

public class InsuranceIdNotFoundException extends MyException {
    public InsuranceIdNotFoundException() {
        super(INSURANCE_ID_NOT_FOUND);
    }
}
