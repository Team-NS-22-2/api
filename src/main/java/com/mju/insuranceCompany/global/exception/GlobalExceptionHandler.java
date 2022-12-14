package com.mju.insuranceCompany.global.exception;


import com.amazonaws.services.s3.model.AmazonS3Exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import java.net.ConnectException;
import java.util.NoSuchElementException;

import static com.mju.insuranceCompany.global.exception.ErrorResponse.createErrorResponse;
import static com.mju.insuranceCompany.global.exception.GlobalErrorCode.*;

@Slf4j
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
                .body(createErrorResponse(NO_SUCH_ELEMENT,request.getRequestURI()));
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<ErrorResponse> handleAmazonS3Exception(NoSuchElementException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createErrorResponse(S3_CONNECT_FAIL,request.getRequestURI()));
    }

    /** DB 커넥션 에러 핸들 */
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ErrorResponse> handleConnectException(ConnectException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createErrorResponse(DB_CONNECT_FAIL,request.getRequestURI()));
    }


}
