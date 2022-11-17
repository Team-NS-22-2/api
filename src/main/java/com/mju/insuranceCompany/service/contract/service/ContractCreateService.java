package com.mju.insuranceCompany.service.contract.service;


import com.mju.insuranceCompany.service.contract.domain.CarContract;
import com.mju.insuranceCompany.service.contract.domain.Contract;
import com.mju.insuranceCompany.service.contract.domain.FireContract;
import com.mju.insuranceCompany.service.contract.domain.HealthContract;
import com.mju.insuranceCompany.service.customer.domain.Customer;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import com.mju.insuranceCompany.service.customer.controller.dto.RegisterCarContractRequest;
import com.mju.insuranceCompany.service.customer.controller.dto.RegisterFireContractRequest;
import com.mju.insuranceCompany.service.customer.controller.dto.RegisterHealthContractRequest;
import com.mju.insuranceCompany.service.customer.controller.dto.RegisterContractResponse;
import com.mju.insuranceCompany.global.exception.MyInvalidAccessException;
import com.mju.insuranceCompany.global.exception.NullDataException;
import com.mju.insuranceCompany.service.user.domain.Users;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import com.mju.insuranceCompany.service.insurance.repository.InsuranceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ContractCreateService {

    private final InsuranceRepository insuranceRepository;
    private final CustomerRepository customerRepository;
    private final ContractRepository contractRepository;

    public RegisterContractResponse registerHealthContract(int insId, RegisterHealthContractRequest request) {

//        getUsers()
//        if (getUsers().getType() == UserType.SALES) {
//            employeeRepository.findByUsername(getUserName())
//        }


        InsuranceType insuranceType = insuranceRepository.findInsuranceTypeByInsuranceId(insId).orElseThrow(NullDataException::new);
        if(insuranceType != InsuranceType.HEALTH) throw new MyInvalidAccessException();

        Customer customer = new Customer(request.getCustomerDto());
        customerRepository.save(customer);

        request.getHealthContractDto().setInsuranceId(insId);
        Contract contract = new HealthContract(request.getHealthContractDto(), customer.getId());
        contractRepository.save(contract);

        return RegisterContractResponse.builder().customerId(customer.getId()).build();
    }

    private Users getUsers(){
        return (Users) SecurityContextHolder.getContext().getAuthentication();
    }

    public RegisterContractResponse registerFireContract(int insId, RegisterFireContractRequest request) {
        InsuranceType insuranceType = insuranceRepository.findInsuranceTypeByInsuranceId(insId).orElseThrow(NullDataException::new);
        if(insuranceType != InsuranceType.FIRE) throw new MyInvalidAccessException();

        Customer customer = new Customer(request.getCustomerDto());
        customer.setId(customerRepository.save(customer).getId());

        request.getFireContractDto().setInsuranceId(insId);
        Contract contract = new FireContract(request.getFireContractDto(), customer.getId());
        contractRepository.save(contract);

        return RegisterContractResponse.builder().customerId(customer.getId()).build();
    }

    public RegisterContractResponse registerCarContract(int insId, RegisterCarContractRequest request) {
        InsuranceType insuranceType = insuranceRepository.findInsuranceTypeByInsuranceId(insId).orElseThrow(NullDataException::new);
        if(insuranceType != InsuranceType.CAR) throw new MyInvalidAccessException();

        Customer customer = new Customer(request.getCustomerDto());
        customerRepository.save(customer);

        request.getCarContractDto().setInsuranceId(insId);

        Contract contract = new CarContract(request.getCarContractDto(), customer.getId());
        contractRepository.save(contract);

        return RegisterContractResponse.builder().customerId(customer.getId()).build();
    }

}