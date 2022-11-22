package com.mju.insuranceCompany.service.user.exception;

import com.mju.insuranceCompany.global.exception.MyException;

import static com.mju.insuranceCompany.service.user.exception.UserErrorCode.UNAUTHORIZED_ACCESS;
public class UnauthorizedAccessException extends MyException {
    public UnauthorizedAccessException() {
        super(UNAUTHORIZED_ACCESS);
    }
}
