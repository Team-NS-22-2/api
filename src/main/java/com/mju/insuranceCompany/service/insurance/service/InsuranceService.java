package com.mju.insuranceCompany.service.insurance.service;

import com.mju.insuranceCompany.service.insurance.exception.InsuranceIdNotFoundException;
import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquirePremiumResponse;
import com.mju.insuranceCompany.service.insurance.repository.InsuranceRepository;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquireCarPremiumRequest;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquireFirePremiumRequest;
import com.mju.insuranceCompany.service.insurance.controller.dto.InquireHealthPremiumRequest;
import com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceListDto;
import com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceGuaranteeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor @Transactional
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    /*
    TODO 이 메소드는 모든 보험정보를 조회하는 메소드다. 하지만 체결을 위한 보험은 판매상태가 PERMISSION 상태여야 한다. 다른 메소드를 만들어서 판매할 때는 해당 메소드를 호출하도록 해야하는가?
     */
    public List<InsuranceListDto> getAllInsuranceList() {
        return insuranceRepository.findAll().stream().map(InsuranceListDto::toDto).toList();
    }
    
    public InsuranceGuaranteeDto getInsuranceGuaranteeById(int id) {
        Insurance insurance = insuranceRepository.findById(id).orElseThrow(InsuranceIdNotFoundException::new);
        return InsuranceGuaranteeDto.toDto(insurance);
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
