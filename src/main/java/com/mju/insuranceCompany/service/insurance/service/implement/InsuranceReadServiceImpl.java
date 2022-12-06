package com.mju.insuranceCompany.service.insurance.service.implement;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.employee.domain.Employee;
import com.mju.insuranceCompany.service.employee.exception.EmployeeIdNotFoundException;
import com.mju.insuranceCompany.service.employee.repository.EmployeeRepository;
import com.mju.insuranceCompany.service.insurance.controller.dto.*;
import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import com.mju.insuranceCompany.service.insurance.exception.InsuranceIdNotFoundException;
import com.mju.insuranceCompany.service.insurance.repository.InsuranceRepository;
import com.mju.insuranceCompany.service.insurance.service.interfaces.InsuranceReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InsuranceReadServiceImpl implements InsuranceReadService {

    private final InsuranceRepository insuranceRepository;
    private final EmployeeRepository employeeRepository;

    private Insurance getInsuranceById(int insuranceId) {
        return insuranceRepository.findById(insuranceId).orElseThrow(InsuranceIdNotFoundException::new);
    }

    /*
    TODO 이 메소드는 모든 보험정보를 조회하는 메소드다. 하지만 체결을 위한 보험은 판매상태가 PERMISSION 상태여야 한다. 다른 메소드를 만들어서 판매할 때는 해당 메소드를 호출하도록 해야하는가?
     */
    @Override
    public List<InsuranceListDto> getAllInsuranceListForSale() {
        return insuranceRepository.findAll().stream().map(InsuranceListDto::toDto).toList();
    }

    @Override
    public InsuranceGuaranteeDto getInsuranceGuaranteeById(int insuranceId) {
        Insurance insurance = getInsuranceById(insuranceId);
        return InsuranceGuaranteeDto.toDto(insurance);
    }

    @Override
    public InsurancePremiumDto inquireHealthPremium(int insuranceId, InquireHealthPremiumDto requestDto) {
        Insurance insurance = getInsuranceById(insuranceId);
        int premium = insurance.inquireHealthPremium(requestDto.getSsn(), requestDto.getRiskCount());
        return InsurancePremiumDto.builder().premium(premium).build();
    }

    @Override
    public InsurancePremiumDto inquireFirePremium(int insuranceId, InquireFirePremiumDto requestDto) {
        Insurance insurance = getInsuranceById(insuranceId);
        int premium = insurance.inquireFirePremium(requestDto.getBuildingType(), requestDto.getCollateralAmount());
        return InsurancePremiumDto.builder().premium(premium).build();
    }

    @Override
    public InsurancePremiumDto inquireCarPremium(int insuranceId, InquireCarPremiumDto requestDto) {
        Insurance insurance = getInsuranceById(insuranceId);
        int premium = insurance.inquireCarPremium(requestDto.getSsn(), requestDto.getValue());
        return InsurancePremiumDto.builder().premium(premium).build();
    }

    @Override
    public List<InsuranceOfDeveloperDto> getInsuranceListOfDeveloper() {
        int employeeId = AuthenticationExtractor.extractEmployeeIdByAuthentication();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeIdNotFoundException::new);
        return insuranceRepository.findInsuranceOfDeveloperByEmployeeId(employee.getId());
    }

    @Override
    public InsurancePremiumDto calculateHealthPremium(CalculateHealthPremiumDto calculateHealthPremiumDto) {
        StandardPremiumDto standardPremiumDto = calculateHealthPremiumDto.getStandardPremiumDto();
        HealthDetailDto healthDetailDto = calculateHealthPremiumDto.getHealthDetailDto();
        int premium = Insurance.calcHealthPremium(standardPremiumDto, healthDetailDto);
        return InsurancePremiumDto.builder().premium(premium).build();
    }

    @Override
    public InsurancePremiumDto calculateCarPremium(CalculateCarPremiumDto calculateCarPremiumDto) {
        StandardPremiumDto standardPremiumDto = calculateCarPremiumDto.getStandardPremiumDto();
        CarDetailDto carDetailDto = calculateCarPremiumDto.getCarDetailDto();
        int premium = Insurance.calcCarPremium(standardPremiumDto, carDetailDto);
        return InsurancePremiumDto.builder().premium(premium).build();
    }

    @Override
    public InsurancePremiumDto calculateFirePremium(CalculateFirePremiumDto calculateFirePremiumDto) {
        StandardPremiumDto standardPremiumDto = calculateFirePremiumDto.getStandardPremiumDto();
        FireDetailDto fireDetailDto = calculateFirePremiumDto.getFireDetailDto();
        int premium = Insurance.calcFirePremium(standardPremiumDto, fireDetailDto);
        return InsurancePremiumDto.builder().premium(premium).build();
    }

    @Override
    public InsuranceForUploadAuthFileDto getInsuranceInfoForUploadAuthFile(int insuranceId) {
        Insurance insurance = getInsuranceById(insuranceId);
        return InsuranceForUploadAuthFileDto.toDto(insurance);
    }
}
