package com.mju.insuranceCompany.global.exception;

public class LoginPwFailedException extends RuntimeException {
    public LoginPwFailedException() {
        super("\033[1;31mLOGIN ERROR:: 패스워드를 다시 확인해주세요.\033[0m");
    }
}
