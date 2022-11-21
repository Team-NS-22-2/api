package com.mju.insuranceCompany.service.contract.repository;

import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContractRepositoryTest {

    @Autowired ContractRepository repository;

    @Test
    void UWTest () throws Exception{

        List<ConditionOfUwOfCustomerResponse> uwOfCustomer = repository.findConditionOfUwOfCustomer(InsuranceType.FIRE);

        for (ConditionOfUwOfCustomerResponse conditionOfUwOfCustomerResponse : uwOfCustomer) {

            System.out.println(conditionOfUwOfCustomerResponse);
        }


    }
}