package com.mju.insuranceCompany.service.customer.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import com.mju.insuranceCompany.global.exception.MyException;

import static com.mju.insuranceCompany.service.customer.exception.CustomerErrorCode.CONTRACT_OF_CUSTOMER_NOT_FOUND;

public class ContractofCustomerNotFoundException extends MyException {
    public ContractofCustomerNotFoundException() {
        super(CONTRACT_OF_CUSTOMER_NOT_FOUND);
    }
}
