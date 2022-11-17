package com.mju.insuranceCompany.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

public class JwtProvider {

    @Value("${jwt.secretkey}")
    private String secretKey;
    @Value("${jwt.accessToken}")
    private long accessTokenExpirationTime;
    @Value("${jwt.refreshToken}")
    private long refreshTokenExpirationTime;

    @PostConstruct // init() 메소드
    protected void init() {  // secretKey를 Base64형식으로 인코딩함. 인코딩 전후 확인 로깅
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }


    private String createToken(long expirationTime, String userId) {
        // 토큰 생성 시작
        Claims claims = Jwts.claims().setSubject(userId);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();

    }

    public String createAccessToken(String loginId) {
        return createToken(accessTokenExpirationTime, loginId);
    }

    public String createRefreshToken(String email) {
        return createToken(refreshTokenExpirationTime, email);
    }

    public String getUserEmail(String token) {
        return  Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
                .getSubject();
    }
    public Claims validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return claims.getBody();

    }
}

