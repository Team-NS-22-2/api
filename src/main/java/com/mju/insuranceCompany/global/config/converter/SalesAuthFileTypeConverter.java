package com.mju.insuranceCompany.global.config.converter;

import com.mju.insuranceCompany.service.insurance.domain.SalesAuthFileType;
import org.springframework.core.convert.converter.Converter;

public class SalesAuthFileTypeConverter implements Converter<String, SalesAuthFileType> {
    @Override
    public SalesAuthFileType convert(String source) {
        return SalesAuthFileType.ofType(source);
    }
}
