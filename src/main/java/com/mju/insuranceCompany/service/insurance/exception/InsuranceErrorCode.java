package com.mju.insuranceCompany.service.insurance.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter @RequiredArgsConstructor
public enum InsuranceErrorCode implements ErrorCode {

    INSURANCE_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "입력하신 ID에 해당하는 보험 정보가 존재하지 않습니다."),
    SALES_AUTH_FILE_TYPE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 파일 타입으로 요청하였습니다."),
    INSUFFICIENT_CONDITIONS_SALES_AUTH_STATE(HttpStatus.BAD_REQUEST, "판매인가파일이 모두 등록되어 있지 않습니다."),
    MISMATCH_INSURANCE_TYPE_AND_REQUEST_CONTRACT(HttpStatus.BAD_REQUEST, "요청하신 계약과 보험의 타입이 일치하지 않습니다."),
    NOT_EXIST_PREMIUM_OF_REQUEST_CONDITION(HttpStatus.NOT_FOUND, "해당 조건에 부합하는 보험료가 찾을 수 없습니다.\n조건을 다시 입력해주세요."),
    STANDARD_PREMIUM_CONDITION_BAD_REQUEST(HttpStatus.BAD_REQUEST, "올바른 순보험료 산출 값을 입력해주세요.");


    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName(){
        return this.name();
    }

}
