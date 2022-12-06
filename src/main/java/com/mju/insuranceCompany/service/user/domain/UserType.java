package com.mju.insuranceCompany.service.user.domain;

/**
 * 작성자 : 김규현
 * 작성일 : 22/05/10
 * 내용 : 보험사 어플리케이션에 나타날 유저 타입에 대해서 작성.
 */
public enum UserType {
    ROLE_CUSTOMER, ROLE_SALES, ROLE_UW, ROLE_DEV, ROLE_COMP, ROLE_OUT;

    public String getRole(){
        return this.name().replace("ROLE_","");
    }
}
