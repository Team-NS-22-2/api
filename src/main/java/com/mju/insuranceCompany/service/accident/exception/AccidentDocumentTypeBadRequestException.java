package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class AccidentDocumentTypeBadRequestException extends MyException {
    public AccidentDocumentTypeBadRequestException() {
        super(AccidentErrorCode.ACCIDENT_DOCUMENT_TYPE_BAD_REQUEST);
    }
}
