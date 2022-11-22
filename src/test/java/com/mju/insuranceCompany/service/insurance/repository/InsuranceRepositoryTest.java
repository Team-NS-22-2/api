package com.mju.insuranceCompany.service.insurance.repository;

import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InsuranceRepositoryTest {

    @Autowired
    InsuranceRepository insuranceRepository;

    @Test
    void getInsurance() {
        int insuranceId = 1;

        Insurance insurance = insuranceRepository.findById(insuranceId).orElseThrow();

        System.out.println(insurance.getDevelopInfo());
    }

}