package com.mju.insuranceCompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InsuranceCompanyApplication {

    /*
    여기서의 핵심은 com.amazonaws.sdk.disableEc2Metadata 속성을 true로 설정해주는것입니다.
    만약 해당 설정을 하지 않을 경우 서비스가 실행되는 시점에 약간의 지연이 발생하고 다음과 같은 예외 메세지가 발생합니다.
     */
    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

    public static void main(String[] args) {
        SpringApplication.run(InsuranceCompanyApplication.class, args);
    }

}
