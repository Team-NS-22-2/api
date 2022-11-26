package com.mju.insuranceCompany.service.contract.repository;

import com.mju.insuranceCompany.service.customer.controller.dto.ContractReceiptDto;
import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
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

    @Transactional
    @Test @DisplayName("고객ID를 통해 결제를 위한 정보를 읽어온다")
    void readContractReceiptByCustomerId () throws Exception{
        //given
        int customerId = 7;
        //when
        List<ContractReceiptDto> receipt = repository.findAllContractReceipt(customerId);
        //then

        for (ContractReceiptDto dto : receipt) {
            System.out.println(dto);
        }
        assertThat(receipt.size()).isSameAs(1);


    }
}