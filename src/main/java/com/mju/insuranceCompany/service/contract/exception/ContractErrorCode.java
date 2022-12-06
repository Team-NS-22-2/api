package com.mju.insuranceCompany.service.contract.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter @RequiredArgsConstructor
public enum ContractErrorCode implements ErrorCode {

    PAYMENT_NOT_REGISTERED(HttpStatus.NOT_FOUND,"[알림] 해당 계약에 대해 결제 수단 정보가 없습니다. 설정해주세요."),


    ;


    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName(){
        return this.name();
    }

}
