package com.mju.insuranceCompany.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.insuranceCompany.service.user.controller.dto.UserBasicRequest;
import com.mju.insuranceCompany.service.user.domain.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
//    private final RefreshTokenService refreshTokenService;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("[JWTAuthorizationFilter] - attemptAuthentication 시작");
        ObjectMapper mapper = new ObjectMapper();
        UserBasicRequest dto;
        try {
            dto = mapper.readValue(request.getInputStream(), UserBasicRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(dto.getUserId(), dto.getPassword());

        return authenticationManager.authenticate(authenticationToken);

    }

    // jwt 토큰을 만들어서 response로 전달해줘서 유저가 받아서 사용할 수 있게끔 한다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("[JWTAuthorizationFilter] - successfulAuthentication 시작");
        Users user = (Users) authResult.getPrincipal();
        String accessToken = jwtProvider.createAccessToken(user.getUserId());
//        String refreshToken = jwtProvider.createRefreshToken(user.getUserId());

//        TokenResponseDto dto = TokenResponseDto.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .build();
//        refreshTokenService.createToken(user.getUsername(),refreshToken);


        response.addHeader("Access-Token", accessToken);
//        response.addHeader("Refresh-Token",dto.getRefreshToken());
        response.setStatus(201);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("[JWTAuthorizationFilter] - 인증실패");
        authenticationFailureHandler.onAuthenticationFailure(request,response,failed);
    }
}
