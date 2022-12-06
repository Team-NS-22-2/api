package com.mju.insuranceCompany.service.insurance.domain;

import com.mju.insuranceCompany.service.insurance.exception.SalesAuthFileTypeBadRequestException;

public enum SalesAuthFileType {
    PROD("prod"),
    ISO("iso"),
    SR_ACTUARY("sracutuary"),
    FSS_OFFICIAL("fssofficial");

    private String typeName;
    SalesAuthFileType(String typeName) {
        this.typeName = typeName;
    }

    public static SalesAuthFileType ofType(String source) {
        for(SalesAuthFileType type : SalesAuthFileType.values()) {
            if(type.typeName.equals(source)) {
                return type;
            }
        }
        throw new SalesAuthFileTypeBadRequestException();
    }
}