package com.mju.insuranceCompany.service.insurance.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter @RequiredArgsConstructor
public enum InsuranceErrorCode implements ErrorCode {

    INSURANCE_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "입력하신 ID에 해당하는 보험 정보가 존재하지 않습니다."),
    SALES_AUTH_FILE_TYPE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 파일 타입으로 요청하였습니다."),
    INSUFFICIENT_CONDITIONS_SALES_AUTH_STATE(HttpStatus.BAD_REQUEST, "판매인가파일이 모두 등록되어 있지 않습니다.")

    ;


    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName(){
        return this.name();
    }

}
