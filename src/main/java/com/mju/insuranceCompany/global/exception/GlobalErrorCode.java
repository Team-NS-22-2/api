package com.mju.insuranceCompany.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode{
    NO_SUCH_ELEMENT(HttpStatus.NOT_FOUND, "정보를 조회할 수 없습니다.")

    ;


    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName(){
        return this.name();
    }
}
