package com.mju.insuranceCompany.service.contract.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter @RequiredArgsConstructor
public enum ContractErrorCode implements ErrorCode {

    PAYMENT_NOT_REGISTERED(HttpStatus.NOT_FOUND,"결제수단이 등록되지 않았습니다."),


    ;


    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName(){
        return this.name();
    }

}
