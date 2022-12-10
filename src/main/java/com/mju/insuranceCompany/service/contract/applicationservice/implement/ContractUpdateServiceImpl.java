package com.mju.insuranceCompany.service.contract.applicationservice.implement;

import com.mju.insuranceCompany.service.contract.domain.ConditionOfUw;
import com.mju.insuranceCompany.service.contract.domain.Contract;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.insuranceCompany.service.contract.applicationservice.interfaces.ContractUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
@RequiredArgsConstructor
public class ContractUpdateServiceImpl implements ContractUpdateService {

    private final ContractRepository contractRepository;

    @Override
    public void underwriting(int contractId, String reasonOfUw, ConditionOfUw conditionOfUw) {
        Contract contract = contractRepository.findById(contractId).orElseThrow();
        contract.underwriting(reasonOfUw, conditionOfUw);
    }



}
