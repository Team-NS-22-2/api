package com.mju.insuranceCompany.global.config;

import com.mju.insuranceCompany.global.config.converter.AccDocTypeConverter;
import com.mju.insuranceCompany.global.config.converter.SalesAuthFileTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","PUT","PATCH","DELETE");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new SalesAuthFileTypeConverter());
        registry.addConverter(new AccDocTypeConverter());
    }
}
