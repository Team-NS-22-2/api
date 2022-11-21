package com.mju.insuranceCompany.service.contract.service;

import com.mju.insuranceCompany.service.contract.controller.dto.*;
import com.mju.insuranceCompany.service.contract.domain.*;
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

    public CustomerHealthContractDto getHealthContractOfCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        HealthContract healthContract = contractRepository.findHealthContractByCustomerId(customerId).orElseThrow();

        return new CustomerHealthContractDto(
                CustomerBasicDto.toDtoFromEntity(customer),
                HealthContractDto.toDtoFromEntity(healthContract)
        );
    }

    public CustomerFireContractDto getFireContractOfCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        FireContract fireContract = contractRepository.findFireContractByCustomerId(customerId).orElseThrow();

        return new CustomerFireContractDto(
                CustomerBasicDto.toDtoFromEntity(customer),
                FireContractDto.toDtoFromEntity(fireContract)
        );
    }

    public CustomerCarContractDto getCarContractOfCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        CarContract carContract = contractRepository.findCarContractByCustomerId(customerId).orElseThrow();

        return new CustomerCarContractDto(
                CustomerBasicDto.toDtoFromEntity(customer),
                CarContractDto.toDtoFromEntity(carContract)
        );
    }

    public void underwriting(int contractId, String reasonOfUw, ConditionOfUw conditionOfUw) {
        Contract contract = contractRepository.findById(contractId).orElseThrow();
        contract.underwriting(reasonOfUw, conditionOfUw);
    }

}
