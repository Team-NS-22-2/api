package com.mju.insuranceCompany.service.contract.service;

import com.mju.insuranceCompany.service.contract.domain.ConditionOfUw;
import com.mju.insuranceCompany.service.contract.domain.Contract;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Slf4j
class ContractServiceTest {

    @Autowired
    ContractService contractService;
    @Autowired
    ContractRepository contractRepository;
    @Test
    void getUwStateOfCustomer() {
        contractService.getUwStateOfCustomer(InsuranceType.HEALTH);
    }

    @Test
    void getHealthContractOfCustomer() {
        System.out.println(contractService.getHealthContractOfCustomer(1));
    }

    @Test
    void underwriting() {
        Contract contract_origin = contractRepository.findById(1).orElseThrow();

        contractService.underwriting(1, "Test Underwriting", ConditionOfUw.APPROVAL);

        Contract contract_update = contractRepository.findById(1).orElseThrow();

        System.out.printf("ORIGIN CONTRACT : {%s}", contract_origin);
        System.out.printf("UPDATE CONTRACT : {%s}", contract_update);

        /*
        contract_origin도 값이 변경된다.
        JPA는 변수를 포인터로 저장해두는 것이 아니라, 영속성으로 객체가 DB랑 연결되어 있는 것인가??

        근데 왜 hibernate에 update 쿼리는 안 찍히는 걸까??
         */
    }
}