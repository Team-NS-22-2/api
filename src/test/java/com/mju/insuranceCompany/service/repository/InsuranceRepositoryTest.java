package com.mju.insuranceCompany.service.repository;

import com.mju.insuranceCompany.service.insurance.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InsuranceRepositoryTest {

    @Autowired
    InsuranceRepository repository;

}