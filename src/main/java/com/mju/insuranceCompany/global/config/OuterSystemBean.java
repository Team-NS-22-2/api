package com.mju.insuranceCompany.global.config;

import com.mju.outerSystem.ElectronicPaymentSystem;
import com.mju.outerSystem.mockobject.MockElectronicPaymentSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OuterSystemBean {

    @Bean
    public ElectronicPaymentSystem electronicPaymentSystem(){
        return new MockElectronicPaymentSystem();
    }
}
