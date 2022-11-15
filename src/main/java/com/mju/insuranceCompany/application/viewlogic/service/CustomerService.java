package com.mju.insuranceCompany.application.viewlogic.service;

import com.mju.insuranceCompany.application.domain.customer.Customer;
import com.mju.insuranceCompany.application.domain.insurance.Insurance;
import com.mju.insuranceCompany.application.global.exception.NullDataException;
import com.mju.insuranceCompany.application.repository.CustomerRepository;
import com.mju.insuranceCompany.application.repository.InsuranceRepository;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.request.InquireHealthPremiumRequestDto;
import com.mju.insuranceCompany.application.viewlogic.dto.insurance.response.InsuranceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final InsuranceRepository insuranceRepository;

    private static final Customer customer = new Customer();

    public int inquireHealthPremium(int insId, InquireHealthPremiumRequestDto requestDto) {
        InsuranceDto insurance = insuranceRepository.findById(insId).orElseThrow(NullDataException::new).toInsuranceDto();

        return customer.inquireHealthPremium(requestDto.getSsn(), requestDto.getRiskCount(), insurance);
    }

}
