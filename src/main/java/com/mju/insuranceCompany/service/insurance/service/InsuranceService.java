package com.mju.insuranceCompany.service.insurance.service;

import com.mju.insuranceCompany.service.insurance.exception.InsuranceIdNotFoundException;
import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquirePremiumResponse;
import com.mju.insuranceCompany.service.insurance.repository.InsuranceRepository;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquireCarPremiumRequest;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquireFirePremiumRequest;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquireHealthPremiumRequest;
import com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceListDto;
import com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceGuaranteeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    public List<InsuranceListDto> getAllInsuranceList() {
        return insuranceRepository.findAll().stream().map(Insurance::toInsuranceListDto).toList();
    }
    
    public InsuranceGuaranteeResponse getInsuranceGuaranteeById(int id) {
        return insuranceRepository.findById(id).orElseThrow(InsuranceIdNotFoundException::new).toInsuranceGuaranteeDto();
    }

    public InquirePremiumResponse inquireHealthPremium(int insId, InquireHealthPremiumRequest requestDto) {
        Insurance insurance = insuranceRepository.findById(insId).orElseThrow(InsuranceIdNotFoundException::new);
        int premium = insurance.inquireHealthPremium(requestDto.getSsn(), requestDto.getRiskCount());
        return InquirePremiumResponse.builder().premium(premium).build();
    }

    public InquirePremiumResponse inquireFirePremium(int insId, InquireFirePremiumRequest requestDto) {
        Insurance insurance = insuranceRepository.findById(insId).orElseThrow(InsuranceIdNotFoundException::new);
        int premium = insurance.inquireFirePremium(requestDto.getBuildingType(), requestDto.getCollateralAmount());
        return InquirePremiumResponse.builder().premium(premium).build();
    }

    public InquirePremiumResponse inquireCarPremium(int insId, InquireCarPremiumRequest requestDto) {
        Insurance insurance = insuranceRepository.findById(insId).orElseThrow(InsuranceIdNotFoundException::new);
        int premium = insurance.inquireCarPremium(requestDto.getSsn(), requestDto.getValue());
        return InquirePremiumResponse.builder().premium(premium).build();
    }

}
