package com.mju.insuranceCompany.application.viewlogic.service;

import com.mju.insuranceCompany.application.domain.customer.Customer;
import com.mju.insuranceCompany.application.global.exception.NullDataException;
import com.mju.insuranceCompany.application.repository.CustomerRepository;
import com.mju.insuranceCompany.application.repository.InsuranceRepository;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.request.InquireCarPremiumRequest;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.request.InquireFirePremiumRequest;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.request.InquireHealthPremiumRequest;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.response.InquirePremiumResponse;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.response.InsuranceCarDetailDto;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.response.InsuranceFireDetailDto;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.response.InsuranceHealthDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final InsuranceRepository insuranceRepository;

    private static final Customer customer = new Customer();

    public InquirePremiumResponse inquireHealthPremium(int insId, InquireHealthPremiumRequest requestDto) {
        InsuranceHealthDetailDto insurance = insuranceRepository.findById(insId).orElseThrow(NullDataException::new).toInsuranceHealthDetailDto();
        int premium = customer.inquireHealthPremium(requestDto.getSsn(), requestDto.getRiskCount(), insurance);
        return InquirePremiumResponse.builder().premium(premium).build();
    }

    public InquirePremiumResponse inquireFirePremium(int insId, InquireFirePremiumRequest requestDto) {
        InsuranceFireDetailDto insurance = insuranceRepository.findById(insId).orElseThrow(NullDataException::new).toInsuranceFireDetailDto();
        int premium = customer.inquireFirePremium(requestDto.getBuildingType(), requestDto.getCollateralAmount(), insurance);
        return InquirePremiumResponse.builder().premium(premium).build();
    }

    public InquirePremiumResponse inquireCarPremium(int insId, InquireCarPremiumRequest requestDto) {
        InsuranceCarDetailDto insurance = insuranceRepository.findById(insId).orElseThrow(NullDataException::new).toInsuranceCarDetailDto();
        int premium = customer.inquireCarPremium(requestDto.getSsn(), requestDto.getValue(), insurance);
        return InquirePremiumResponse.builder().premium(premium).build();
    }
}
