package com.mju.insuranceCompany.global.exception.customer;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter @RequiredArgsConstructor
public enum CustomerErrorCode implements ErrorCode {

    ;


    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName(){
        return this.name();
    }

}
