package com.mju.insuranceCompany.service.user.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class DuplicateUserIdException extends MyException {
    public DuplicateUserIdException() {
        super(UserErrorCode.DUPLICATE_USER_ID);
    }
}
