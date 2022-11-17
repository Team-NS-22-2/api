package com.mju.insuranceCompany.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException exception) throws IOException, ServletException {
        log.info("[CustomAuthenticationFailureHandler] - 필터 예외 처리");
        ObjectMapper mapper = new ObjectMapper();
//        ErrorResponse errorResponse = ErrorResponse.createErrorResponse(new LoginDataNotFoundException(), request.getRequestURI());
//        String errorMessage = mapper.writeValueAsString(errorResponse);

        response.setStatus(404);
        response.setCharacterEncoding("UTF-8");
        response.getWriter();
//                .println(errorMessage);
    }
}