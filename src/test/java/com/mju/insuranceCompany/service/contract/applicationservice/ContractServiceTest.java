package com.mju.insuranceCompany.service.contract.applicationservice;

import com.mju.insuranceCompany.service.contract.controller.dto.CustomerFireContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.FireContractDto;
import com.mju.insuranceCompany.service.contract.domain.BuildingType;
import com.mju.insuranceCompany.service.contract.domain.ConditionOfUw;
import com.mju.insuranceCompany.service.contract.domain.Contract;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.insuranceCompany.service.contract.applicationservice.implement.ContractCreateServiceImpl;
import com.mju.insuranceCompany.service.contract.applicationservice.interfaces.ContractReadService;
import com.mju.insuranceCompany.service.contract.applicationservice.interfaces.ContractUpdateService;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("prod")
class ContractServiceTest {

    @Autowired
    ContractReadService contractReadService;
    @Autowired
    ContractUpdateService contractUpdateService;
    @Autowired
    ContractCreateServiceImpl contractCreateService;
    @Autowired ContractRepository contractRepository;
    @Autowired CustomerRepository customerRepository;

//    @Test
    void getUwStateOfCustomer() {
        List<ConditionOfUwOfCustomerResponse> h = contractReadService.getUwStateOfCustomer(InsuranceType.HEALTH);
        List<ConditionOfUwOfCustomerResponse> c = contractReadService.getUwStateOfCustomer(InsuranceType.CAR);
        List<ConditionOfUwOfCustomerResponse> f = contractReadService.getUwStateOfCustomer(InsuranceType.FIRE);

        for(ConditionOfUwOfCustomerResponse r : h) {
            Assertions.assertThat(r.getConditionOfUw()).isNotEqualByComparingTo(ConditionOfUw.APPROVAL);
            Assertions.assertThat(r.getConditionOfUw()).isNotEqualByComparingTo(ConditionOfUw.REFUSE);
        }

        for(ConditionOfUwOfCustomerResponse r : c) {
            Assertions.assertThat(r.getConditionOfUw()).isNotEqualByComparingTo(ConditionOfUw.APPROVAL);
            Assertions.assertThat(r.getConditionOfUw()).isNotEqualByComparingTo(ConditionOfUw.REFUSE);
        }

        for(ConditionOfUwOfCustomerResponse r : f) {
            Assertions.assertThat(r.getConditionOfUw()).isNotEqualByComparingTo(ConditionOfUw.APPROVAL);
            Assertions.assertThat(r.getConditionOfUw()).isNotEqualByComparingTo(ConditionOfUw.REFUSE);
        }
    }

    @Test
    void getHealthContractOfCustomer() {
        System.out.println(contractReadService.getHealthContractOfCustomerByContractId(1));
    }

    @Test
    void getCarContractOfCustomer() {
        System.out.println(contractReadService.getCarContractOfCustomerByContractId(11));
    }

    @Test
    void getFireContractOfCustomer() {
        int contractId = 14;
        CustomerFireContractDto dto = contractReadService.getFireContractOfCustomerByContractId(contractId);
        FireContractDto f = dto.getFireContractDto();

        int buildingArea = 300;
        BuildingType buildingType = BuildingType.COMMERCIAL;
        Long collateralAmount = 100000000L;
        Boolean isActualResidence = false;
        Boolean isSelfOwned = true;

        Assertions.assertThat(f.getBuildingArea()).isEqualTo(buildingArea);
        Assertions.assertThat(f.getBuildingType()).isEqualByComparingTo(buildingType);
        Assertions.assertThat(f.getCollateralAmount()).isEqualTo(collateralAmount);
        Assertions.assertThat(f.getIsActualResidence()).isEqualTo(isActualResidence);
        Assertions.assertThat(f.getIsSelfOwned()).isEqualTo(isSelfOwned);
    }

    @Test
    void underwriting() {
        contractUpdateService.underwriting(1, "Test Underwriting", ConditionOfUw.APPROVAL);

        Contract contract = contractRepository.findById(1).orElseThrow();

        Assertions.assertThat(contract.getReasonOfUw()).isEqualTo("Test Underwriting");
        Assertions.assertThat(contract.getConditionOfUw()).isEqualByComparingTo(ConditionOfUw.APPROVAL);

        /*
        contract_origin도 값이 변경된다.
        JPA는 변수를 포인터로 저장해두는 것이 아니라, 영속성으로 객체가 DB랑 연결되어 있는 것인가??

        근데 왜 hibernate에 update 쿼리는 안 찍히는 걸까??
         */
    }
}