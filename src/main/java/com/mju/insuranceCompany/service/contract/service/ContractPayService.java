package com.mju.insuranceCompany.service.contract.service;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.contract.domain.Contract;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.outerSystem.ElectronicPaymentSystem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional @RequiredArgsConstructor
public class ContractPayService {

    private final ContractRepository contractRepository;
    private final ElectronicPaymentSystem electronicPaymentSystem;

    public void payForContractPremium(int contractId){
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        Contract contract = contractRepository.findContractByIdAndCustomerId(contractId, customerId).orElseThrow();
        contract.payPremium(electronicPaymentSystem);

    }
}
