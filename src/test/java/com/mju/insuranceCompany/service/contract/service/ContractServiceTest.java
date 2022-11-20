package com.mju.insuranceCompany.service.contract.service;

import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ContractServiceTest {

    @Autowired
    ContractService contractService;

    @Test
    void getUwStateOfCustomer() {
        contractService.getUwStateOfCustomer(InsuranceType.HEALTH);
    }
}