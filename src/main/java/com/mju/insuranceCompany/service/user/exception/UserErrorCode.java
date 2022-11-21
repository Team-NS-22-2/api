package com.mju.insuranceCompany.service.user.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter @RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "권한이 없는 접근입니다."),
    USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없는 ID입니다.")
    ;


    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName(){
        return this.name();
    }

}
