package com.mju.insurancecompany.original.application.global.exception;

import static insuranceCompany.application.global.constant.ExceptionConstants.INPUT_INVALID_MENU_EXCEPTION;

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
        super(INPUT_INVALID_MENU_EXCEPTION);
    }

    public InputInvalidMenuException(String message) {
        super(INPUT_INVALID_MENU_EXCEPTION);
    }

    public InputInvalidMenuException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputInvalidMenuException(Throwable cause) {
        super(cause);
    }
}
