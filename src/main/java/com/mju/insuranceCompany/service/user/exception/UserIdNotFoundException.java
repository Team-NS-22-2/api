package com.mju.insuranceCompany.service.user.exception;

import com.mju.insuranceCompany.global.exception.MyException;

import static com.mju.insuranceCompany.service.user.exception.UserErrorCode.USER_ID_NOT_FOUND;

public class UserIdNotFoundException extends MyException {
    public UserIdNotFoundException() {
        super(USER_ID_NOT_FOUND);
    }
}
