package com.mju.insuranceCompany.service.auth.exception;

import com.mju.insuranceCompany.global.exception.MyException;

import static com.mju.insuranceCompany.service.auth.exception.AuthErrorCode.USER_ID_NOT_FOUND;

public class UserIdNotFoundException extends MyException {
    public UserIdNotFoundException() {
        super(USER_ID_NOT_FOUND);
    }
}
