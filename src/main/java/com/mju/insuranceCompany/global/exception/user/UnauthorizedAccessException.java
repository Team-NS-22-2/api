package com.mju.insuranceCompany.global.exception.user;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import com.mju.insuranceCompany.global.exception.MyException;

import static com.mju.insuranceCompany.global.exception.user.UserErrorCode.UNAUTHORIZED_ACCESS;
public class UnauthorizedAccessException extends MyException {
    public UnauthorizedAccessException() {
        super(UNAUTHORIZED_ACCESS);
    }
}
