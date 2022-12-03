package com.mju.insuranceCompany.global.config.converter;

import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccDocType;
import org.springframework.core.convert.converter.Converter;

public class AccDocTypeConverter implements Converter<String, AccDocType> {

    @Override
    public AccDocType convert(String source) {
        return AccDocType.ofType(source.toLowerCase());
    }
}
