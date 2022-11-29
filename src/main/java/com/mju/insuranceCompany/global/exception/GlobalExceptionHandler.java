package com.mju.insuranceCompany.global.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

import static com.mju.insuranceCompany.global.exception.ErrorResponse.createErrorResponse;
import static com.mju.insuranceCompany.global.exception.GlobalErrorCode.NO_SUCH_ELEMENT;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<ErrorResponse> handleMyException(MyException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(createErrorResponse(ex,request.getRequestURI()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createErrorResponse(NO_SUCH_ELEMENT, request.getRequestURI()));
    }

}
