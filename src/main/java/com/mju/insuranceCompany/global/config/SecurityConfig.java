package com.mju.insuranceCompany.global.config;

import com.mju.insuranceCompany.global.jwt.*;
import com.mju.insuranceCompany.service.user.domain.UserType;
import com.mju.insuranceCompany.service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userDetailsService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;
//    private final RefreshTokenService refreshTokenService;

    AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
        authenticationManager = authenticationManagerBuilder.build();

        return httpSecurity
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/emp/uw/**").hasRole(UserType.ROLE_UW.getRole())
                .antMatchers("/emp/sales/**").hasRole(UserType.ROLE_SALES.getRole())
                .antMatchers("/emp/dev/**").hasRole(UserType.ROLE_DEV.getRole())
                .antMatchers("/emp/comp/**").hasRole(UserType.ROLE_COMP.getRole())
                .antMatchers("/**/**").permitAll()
                .antMatchers("/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authenticationManager(authenticationManager)
                .addFilterBefore(new JwtAuthorizationFilter(authenticationManager,jwtProvider,  authenticationFailureHandler())
                        , UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider,userDetailsService)
                        ,JwtAuthorizationFilter.class)
                .addFilterBefore(new JwtExceptionHandlingFilter(),
                        JwtAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT","PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}