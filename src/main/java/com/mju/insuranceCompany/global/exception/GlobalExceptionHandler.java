package com.mju.insuranceCompany.global.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.mju.insuranceCompany.global.exception.ErrorResponse.createErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<ErrorResponse> handleMyException(MyException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(createErrorResponse(ex,request.getRequestURI()));
    }
}
