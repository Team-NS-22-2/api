package com.mju.insuranceCompany.global.exception;

import com.mju.insuranceCompany.global.constant.ExceptionConstants;

/**
 * packageName :  insuranceCompany.application.global.exception
 * fileName : InvalidMenuException
 * author :  규현
 * date : 2022-06-02
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-06-02                규현             최초 생성
 */
public class InputInvalidMenuException extends InputException{
    public InputInvalidMenuException() {
        super(ExceptionConstants.INPUT_INVALID_MENU_EXCEPTION);
    }

    public InputInvalidMenuException(String message) {
        super(ExceptionConstants.INPUT_INVALID_MENU_EXCEPTION);
    }

    public InputInvalidMenuException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputInvalidMenuException(Throwable cause) {
        super(cause);
    }
}
