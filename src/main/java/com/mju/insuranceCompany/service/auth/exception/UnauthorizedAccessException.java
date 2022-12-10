package com.mju.insuranceCompany.service.auth.exception;

import com.mju.insuranceCompany.global.exception.MyException;

import static com.mju.insuranceCompany.service.auth.exception.AuthErrorCode.UNAUTHORIZED_ACCESS;
public class UnauthorizedAccessException extends MyException {
    public UnauthorizedAccessException() {
        super(UNAUTHORIZED_ACCESS);
    }
}
