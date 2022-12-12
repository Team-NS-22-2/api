package com.mju.insuranceCompany.global.config.converter;

import com.mju.insuranceCompany.service.insurance.domain.SalesAuthFileType;
import com.mju.insuranceCompany.service.insurance.domain.SalesAuthorizationState;
import org.springframework.core.convert.converter.Converter;

public class SalesAuthStateTypeConverter implements Converter<String, SalesAuthorizationState> {
    @Override
    public SalesAuthorizationState convert(String source) {
        return SalesAuthorizationState.valueOf(source.toUpperCase());
    }
}
