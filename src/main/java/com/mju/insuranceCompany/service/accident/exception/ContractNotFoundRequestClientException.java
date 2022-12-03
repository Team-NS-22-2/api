package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class ContractNotFoundRequestClientException extends MyException {
    public ContractNotFoundRequestClientException() {
        super(AccidentErrorCode.CONTRACT_NOT_FOUND_REQUEST_CLIENT);
    }
}
