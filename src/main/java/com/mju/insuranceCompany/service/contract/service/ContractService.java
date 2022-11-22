package com.mju.insuranceCompany.service.contract.service;

import com.mju.insuranceCompany.service.contract.controller.dto.*;
import com.mju.insuranceCompany.service.contract.domain.*;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.insuranceCompany.service.customer.controller.dto.CustomerDto;
import com.mju.insuranceCompany.service.customer.domain.Customer;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceBasicInfoDto;
import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import com.mju.insuranceCompany.service.insurance.repository.InsuranceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;
    private final InsuranceRepository insuranceRepository;

    public List<ConditionOfUwOfCustomerResponse> getUwStateOfCustomer(InsuranceType insuranceType) {
        return contractRepository.findConditionOfUwOfCustomer(insuranceType);
    }

    public CustomerHealthContractDto getHealthContractOfCustomerByContractId(int contractId) {
        HealthContract healthContract = contractRepository.findHealthContractById(contractId).orElseThrow();
        Customer customer = customerRepository.findById(healthContract.getCustomerId()).orElseThrow();
        Insurance insurance = insuranceRepository.findById(healthContract.getInsuranceId()).orElseThrow();

        return new CustomerHealthContractDto(
                CustomerDto.toDtoFromEntity(customer),
                HealthContractDto.toDtoFromEntity(healthContract),
                InsuranceBasicInfoDto.toDtoFromEntity(insurance)
        );
    }

    public CustomerFireContractDto getFireContractOfCustomerByContractId(int contractId) {
        FireContract fireContract = contractRepository.findFireContractById(contractId).orElseThrow();
        Customer customer = customerRepository.findById(fireContract.getCustomerId()).orElseThrow();
        Insurance insurance = insuranceRepository.findById(fireContract.getInsuranceId()).orElseThrow();

        return new CustomerFireContractDto(
                CustomerDto.toDtoFromEntity(customer),
                FireContractDto.toDtoFromEntity(fireContract),
                InsuranceBasicInfoDto.toDtoFromEntity(insurance)
        );
    }

    public CustomerCarContractDto getCarContractOfCustomerByContractId(int contractId) {
        CarContract carContract = contractRepository.findCarContractById(contractId).orElseThrow();
        Customer customer = customerRepository.findById(carContract.getCustomerId()).orElseThrow();
        Insurance insurance = insuranceRepository.findById(carContract.getInsuranceId()).orElseThrow();

        return new CustomerCarContractDto(
                CustomerDto.toDtoFromEntity(customer),
                CarContractDto.toDtoFromEntity(carContract),
                InsuranceBasicInfoDto.toDtoFromEntity(insurance)
        );
    }

    public void underwriting(int contractId, String reasonOfUw, ConditionOfUw conditionOfUw) {
        Contract contract = contractRepository.findById(contractId).orElseThrow();
        contract.underwriting(reasonOfUw, conditionOfUw);
    }

}
