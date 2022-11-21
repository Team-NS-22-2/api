package com.mju.insuranceCompany.global.exception.user;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import com.mju.insuranceCompany.global.exception.MyException;

import static com.mju.insuranceCompany.global.exception.user.UserErrorCode.USER_ID_NOT_FOUND;

public class UserIdNotFoundException extends MyException {
    public UserIdNotFoundException() {
        super(USER_ID_NOT_FOUND);
    }
}
