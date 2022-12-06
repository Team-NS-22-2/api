package com.mju.insuranceCompany.service.employee.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter @RequiredArgsConstructor
public enum EmployeeErrorCode implements ErrorCode {

    EMPLOYEE_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "입력하신 ID에 해당하는 직원 정보가 존재하지 않습니다.")

    ;


    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName(){
        return this.name();
    }

}
