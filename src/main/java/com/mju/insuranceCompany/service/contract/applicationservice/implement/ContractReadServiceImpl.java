package com.mju.insuranceCompany.service.contract.applicationservice.implement;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.contract.controller.dto.*;
import com.mju.insuranceCompany.service.contract.domain.*;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.insuranceCompany.service.contract.applicationservice.interfaces.ContractReadService;
import com.mju.insuranceCompany.service.customer.controller.dto.ContractReceiptDto;
import com.mju.insuranceCompany.service.customer.controller.dto.CustomerDto;
import com.mju.insuranceCompany.service.customer.domain.Customer;
import com.mju.insuranceCompany.service.customer.exception.ContractofCustomerNotFoundException;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceBasicInfoDto;
import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import com.mju.insuranceCompany.service.insurance.repository.InsuranceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractReadServiceImpl implements ContractReadService {

    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;
    private final InsuranceRepository insuranceRepository;

    @Override
    public List<ConditionOfUwOfCustomerResponse> getUwStateOfCustomer(InsuranceType insuranceType) {
        return contractRepository.findConditionOfUwOfCustomer(insuranceType);
    }

    @Override
    public CustomerHealthContractDto getHealthContractOfCustomerByContractId(int contractId) {
        HealthContract healthContract = contractRepository.findHealthContractById(contractId).orElseThrow();
        Customer customer = customerRepository.findById(healthContract.getCustomerId()).orElseThrow();
        Insurance insurance = insuranceRepository.findById(healthContract.getInsuranceId()).orElseThrow();

        return new CustomerHealthContractDto(
                CustomerDto.toDto(customer),
                HealthContractDto.toDtoFromEntity(healthContract),
                InsuranceBasicInfoDto.toDto(insurance)
        );
    }

    @Override
    public CustomerFireContractDto getFireContractOfCustomerByContractId(int contractId) {
        FireContract fireContract = contractRepository.findFireContractById(contractId).orElseThrow();
        Customer customer = customerRepository.findById(fireContract.getCustomerId()).orElseThrow();
        Insurance insurance = insuranceRepository.findById(fireContract.getInsuranceId()).orElseThrow();

        return new CustomerFireContractDto(
                CustomerDto.toDto(customer),
                FireContractDto.toDtoFromEntity(fireContract),
                InsuranceBasicInfoDto.toDto(insurance)
        );
    }

    @Override
    public CustomerCarContractDto getCarContractOfCustomerByContractId(int contractId) {
        CarContract carContract = contractRepository.findCarContractById(contractId).orElseThrow();
        Customer customer = customerRepository.findById(carContract.getCustomerId()).orElseThrow();
        Insurance insurance = insuranceRepository.findById(carContract.getInsuranceId()).orElseThrow();

        return new CustomerCarContractDto(
                CustomerDto.toDto(customer),
                CarContractDto.toDtoFromEntity(carContract),
                InsuranceBasicInfoDto.toDto(insurance)
        );
    }

    @Override
    public List<ContractReceiptDto> getAllContractReceipts(){
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        List<ContractReceiptDto> receipts = contractRepository.findAllContractReceipt(customerId);
        if(receipts.isEmpty())
            throw new ContractofCustomerNotFoundException();
        return receipts;
    }

}
