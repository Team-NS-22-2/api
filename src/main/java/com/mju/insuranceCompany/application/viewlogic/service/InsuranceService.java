package com.mju.insuranceCompany.application.viewlogic.service;

import com.mju.insuranceCompany.application.domain.insurance.Insurance;
import com.mju.insuranceCompany.application.global.exception.NullDataException;
import com.mju.insuranceCompany.application.repository.InsuranceRepository;
import com.mju.insuranceCompany.application.viewlogic.dto.insurance.response.InsuranceListDto;
import com.mju.insuranceCompany.application.viewlogic.dto.insurance.response.InsuranceGuaranteeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository repository;

    public List<InsuranceListDto> getAllInsuranceList() {
        return repository.findAll().stream().map(Insurance::toInsuranceListDto).toList();
    }
    
    public InsuranceGuaranteeDto getInsuranceGuaranteeById(int id) {
        return repository.findById(id).orElseThrow(NullDataException::new).toInsuranceGuaranteeDto();
    }

}
