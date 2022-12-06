package com.mju.insuranceCompany.service.contract.repository;

import com.mju.insuranceCompany.service.contract.domain.Contract;
import com.mju.insuranceCompany.service.customer.controller.dto.ContractReceiptDto;
import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("prod")
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
    @Test
    void readContractForPay () throws Exception{
        //given
        int contractId = 8;
        int customerId = 8;
        Contract contract = repository.findContractByIdAndCustomerId(contractId, customerId).orElseThrow();
        //when

        //then

        SoftAssertions.assertSoftly(sa -> {
            assertThat(contract.getCustomerId()).isSameAs(customerId);
            assertThat(contract.getId()).isSameAs(contractId);
        });


    }
}