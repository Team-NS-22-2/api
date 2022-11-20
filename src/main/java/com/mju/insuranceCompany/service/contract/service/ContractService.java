package com.mju.insuranceCompany.service.contract.service;

import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    public List<ConditionOfUwOfCustomerResponse> getUwStateOfCustomer(InsuranceType insuranceType) {
        return contractRepository.findConditionOfUwOfCustomer(insuranceType);
    }


}
