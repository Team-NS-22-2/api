package com.mju.insuranceCompany.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor @AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private String timeStamp;
    private HttpStatus httpStatus;
    private String errorName;
    private String errorMessage;
    private String path;

    public static ErrorResponse createErrorResponse(ErrorCode errorCode, String path) {
        return new ErrorResponse(LocalDateTime.now().toString(),errorCode.getHttpStatus()
        , errorCode.getErrorName(), errorCode.getErrorMessage(),path);
    }

    @Override
    public String toString() {
        return "{" +
                "timeStamp=" + timeStamp +
                ", httpStatus=" + httpStatus +
                ", errorName='" + errorName + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
