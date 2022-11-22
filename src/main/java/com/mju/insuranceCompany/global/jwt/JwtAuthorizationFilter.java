package com.mju.insuranceCompany.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.insuranceCompany.service.user.controller.dto.UserBasicRequest;
import com.mju.insuranceCompany.service.user.domain.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor @Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request에서 값을 가져온다
        log.info("[JwtAuthorizationFilter] - filter 시작");
        String authorization = request.getHeader("Authorization");
        String token = null;
        // token 변환
        if (StringUtils.hasText(authorization) && authorization.contains("Bearer")) {
            token = authorization.replace("Bearer","");
            log.info("[JwtAuthorizationFilter] - 토큰변환 = {}",token);
        }
        // 토큰 검증
        if (token != null && jwtProvider.validateToken(token) != null) {
            String loginId = jwtProvider.validateToken(token).getSubject();
            log.info("[JwtAuthorizationFilter] - 토큰검증-로그인아이디 = {}",loginId);
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(loginId));
        }
        log.info("[JwtAuthorizationFilter] - filter 끝");
        filterChain.doFilter(request,response);
    }
    private UsernamePasswordAuthenticationToken getAuthentication(String loginId) {
        UserDetails users = userService.loadUserByUsername(loginId);
        return new UsernamePasswordAuthenticationToken(users, users.getPassword(), users.getAuthorities());
    }
}
