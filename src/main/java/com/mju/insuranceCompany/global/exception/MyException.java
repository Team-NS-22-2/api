package com.mju.insuranceCompany.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * packageName :  main.exception
 * fileName : MyException
 * author :  규현
 * date : 2022-05-11
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-11                규현             최초 생성
 */
@Getter
public abstract class MyException extends RuntimeException implements ErrorCode{

    private final HttpStatus httpStatus;
    private final String errorName;
    private final String errorMessage;
    protected MyException(HttpStatus httpStatus, String errorName, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorName = errorName;
        this.errorMessage = errorMessage;
    }
}
