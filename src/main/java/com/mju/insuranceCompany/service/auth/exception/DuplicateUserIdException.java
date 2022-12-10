package com.mju.insuranceCompany.service.auth.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class DuplicateUserIdException extends MyException {
    public DuplicateUserIdException() {
        super(AuthErrorCode.DUPLICATE_USER_ID);
    }
}
