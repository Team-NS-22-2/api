package com.mju.insuranceCompany.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode{
    NO_SUCH_ELEMENT(HttpStatus.NOT_FOUND, "정보를 조회할 수 없습니다."),
    DB_CONNECT_FAIL(HttpStatus.SERVICE_UNAVAILABLE, "현재 시스템 오류로 인해 원할한 작동이 불가능합니다.\n" +
            "고객센터(1588-1588)로 연락 주시면 해결하겠습니다.")

    ;


    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName(){
        return this.name();
    }
}
