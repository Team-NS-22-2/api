package com.mju.insuranceCompany.service.customer.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter @RequiredArgsConstructor
public enum CustomerErrorCode implements ErrorCode {

    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제수단 정보가 존재하지 않습니다."),
    CONTRACT_OF_CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "고객에게 가입된 계약 정보를 찾을 수 없습니다")

    ;


    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName(){
        return this.name();
    }

}
