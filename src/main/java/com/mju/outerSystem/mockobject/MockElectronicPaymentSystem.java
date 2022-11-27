package com.mju.outerSystem.mockobject;

import com.mju.insuranceCompany.service.customer.domain.payment.Payment;
import com.mju.outerSystem.ElectronicPaymentSystem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockElectronicPaymentSystem implements ElectronicPaymentSystem {
    @Override
    public void pay(Payment payment, int premium) {
        log.info("{}원 결제 완료했습니다.",premium);
    }
}
