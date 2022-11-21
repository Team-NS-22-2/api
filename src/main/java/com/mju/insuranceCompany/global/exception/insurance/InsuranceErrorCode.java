package com.mju.insuranceCompany.global.exception.insurance;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter @RequiredArgsConstructor
public enum InsuranceErrorCode implements ErrorCode {

    ;


    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName(){
        return this.name();
    }

}
