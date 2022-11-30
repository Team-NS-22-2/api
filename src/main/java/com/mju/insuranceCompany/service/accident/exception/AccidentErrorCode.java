package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter @RequiredArgsConstructor
public enum AccidentErrorCode implements ErrorCode {

    CANNOT_REPORT_CAR_ACCIDENT(HttpStatus.BAD_REQUEST, "고객님은 가입된 자동차보험이 없어 자동차사고를 접수하실 수 없습니다."),
    NOT_EXIST_REQUESTED_CAR_NO(HttpStatus.BAD_REQUEST, "해당 차량 번호로 가입되어 있는 자동차보험이 없습니다."),
    CANNOT_REPORT_FIRE_ACCIDENT(HttpStatus.BAD_REQUEST, "고객님은 가입된 화재보험이 없어 화재사고를 접수하실 수 없습니다."),
    CANNOT_REPORT_INJURY_ACCIDENT(HttpStatus.BAD_REQUEST, "고객님은 가입된 건강보험이 없어 상해사고를 접수하실 수 없습니다."),
    ON_SITE_SYSTEM_RESPONSE_ERROR(HttpStatus.GATEWAY_TIMEOUT, "고장은 정상적으로 접수되었으나 현장출동 시스템 오류로 인해 현장출동 요청이 불가능합니다.\n고객센터(1588-1588)로 연락주시면 감사하겠습니다.")
    ;


    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName(){
        return this.name();
    }

}
