package com.mju.insuranceCompany.application.viewlogic.service;

import com.mju.insuranceCompany.application.domain.contract.Contract;
import com.mju.insuranceCompany.application.domain.contract.HealthContract;
import com.mju.insuranceCompany.application.domain.customer.Customer;
import com.mju.insuranceCompany.application.domain.insurance.Insurance;
import com.mju.insuranceCompany.application.domain.insurance.InsuranceDetail;
import com.mju.insuranceCompany.application.domain.insurance.InsuranceType;
import com.mju.insuranceCompany.application.global.exception.MyInvalidAccessException;
import com.mju.insuranceCompany.application.global.exception.NullDataException;
import com.mju.insuranceCompany.application.repository.ContractRepository;
import com.mju.insuranceCompany.application.repository.CustomerRepository;
import com.mju.insuranceCompany.application.repository.InsuranceRepository;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.request.*;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.response.InquirePremiumResponse;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.response.RegisterContractResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final InsuranceRepository insuranceRepository;
    private final ContractRepository contractRepository;

    private static final Customer customerInstance = new Customer();

    public InquirePremiumResponse inquireHealthPremium(int insId, InquireHealthPremiumRequest requestDto) {
        List<InsuranceDetail> healthDetails = insuranceRepository.findHealthDetailsByInsuranceId(insId);
        int premium = customerInstance.inquireHealthPremium(requestDto.getSsn(), requestDto.getRiskCount(), healthDetails);
        return InquirePremiumResponse.builder().premium(premium).build();
    }

    public InquirePremiumResponse inquireFirePremium(int insId, InquireFirePremiumRequest requestDto) {
        List<InsuranceDetail> fireDetails = insuranceRepository.findFireDetailsByInsuranceId(insId);
        int premium = customerInstance.inquireFirePremium(requestDto.getBuildingType(), requestDto.getCollateralAmount(), fireDetails);
        return InquirePremiumResponse.builder().premium(premium).build();
    }

    public InquirePremiumResponse inquireCarPremium(int insId, InquireCarPremiumRequest requestDto) {
        List<InsuranceDetail> carDetails = insuranceRepository.findCarDetailsByInsuranceId(insId);
        int premium = customerInstance.inquireCarPremium(requestDto.getSsn(), requestDto.getValue(), carDetails);
        return InquirePremiumResponse.builder().premium(premium).build();
    }

    public RegisterContractResponse registerHealthContract(int insId, RegisterHealthContractRequest request) {
        InsuranceType insuranceType = insuranceRepository.findInsuranceTypeByInsuranceId(insId).orElseThrow(NullDataException::new);
        if(insuranceType != InsuranceType.HEALTH) throw new MyInvalidAccessException();

        Customer customer = customerInstance.registerCustomer(request.getCustomerDto());
        customer.setId(customerRepository.save(customer).getId());

        Contract contract = customerInstance.registerContract(customer, request.getHealthContractDto(), insId, insuranceType);
        contract.setId(contractRepository.save(contract).getId());

        return RegisterContractResponse.builder().customerId(customer.getId()).build();
    }

    public RegisterContractResponse registerFireContract(int insId, RegisterFireContractRequest request) {
        InsuranceType insuranceType = insuranceRepository.findInsuranceTypeByInsuranceId(insId).orElseThrow(NullDataException::new);
        if(insuranceType != InsuranceType.FIRE) throw new MyInvalidAccessException();

        Customer customer = customerInstance.registerCustomer(request.getCustomerDto());
        customer.setId(customerRepository.save(customer).getId());

        Contract contract = customerInstance.registerContract(customer, request.getFireContractDto(), insId, insuranceType);
        contract.setId(contractRepository.save(contract).getId());

        return RegisterContractResponse.builder().customerId(customer.getId()).build();
    }

    public RegisterContractResponse registerCarContract(int insId, RegisterCarContractRequest request) {
        InsuranceType insuranceType = insuranceRepository.findInsuranceTypeByInsuranceId(insId).orElseThrow(NullDataException::new);
        if(insuranceType != InsuranceType.CAR) throw new MyInvalidAccessException();

        Customer customer = customerInstance.registerCustomer(request.getCustomerDto());
        customer.setId(customerRepository.save(customer).getId());

        Contract contract = customerInstance.registerContract(customer, request.getCarContractDto(), insId, insuranceType);
        contract.setId(contractRepository.save(contract).getId());

        return RegisterContractResponse.builder().customerId(customer.getId()).build();
    }
}
