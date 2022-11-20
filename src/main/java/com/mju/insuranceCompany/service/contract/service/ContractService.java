package com.mju.insuranceCompany.service.contract.service;

import com.mju.insuranceCompany.service.contract.controller.dto.CustomerHealthContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.HealthContractDto;
import com.mju.insuranceCompany.service.contract.domain.HealthContract;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.insuranceCompany.service.customer.controller.dto.CustomerBasicDto;
import com.mju.insuranceCompany.service.customer.domain.Customer;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;

    public List<ConditionOfUwOfCustomerResponse> getUwStateOfCustomer(InsuranceType insuranceType) {
        return contractRepository.findConditionOfUwOfCustomer(insuranceType);
    }


    public void getHealthContractOfCustomer(int cId) {
        Customer customer = customerRepository.findById(cId).orElseThrow();
        HealthContract healthContract = contractRepository.findHealthContractByCustomerId(cId).orElseThrow();

        CustomerHealthContractDto dto = new CustomerHealthContractDto(
                new CustomerBasicDto(
                        customer.getName(),
                        customer.getSsn(),
                        customer.getPhone(),
                        customer.getAddress(),
                        customer.getEmail(),
                        customer.getJob()
                ),
                new HealthContractDto(
                        healthContract.getHeight(),
                        healthContract.getWeight(),
                        healthContract.isDrinking(),
                        healthContract.isSmoking(),
                        healthContract.isDriving(),
                        healthContract.isDangerActivity(),
                        healthContract.isHavingDisease(),
                        healthContract.isTakingDrug(),
                        healthContract.getDiseaseDetail()
                )
        );

        System.out.println(dto);
    }
}
