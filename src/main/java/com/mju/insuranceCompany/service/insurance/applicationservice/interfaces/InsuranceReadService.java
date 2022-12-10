package com.mju.insuranceCompany.service.insurance.applicationservice.interfaces;

import com.mju.insuranceCompany.service.insurance.controller.dto.*;

import java.util.List;

public interface InsuranceReadService {

    List<InsuranceListDto> getAllInsuranceListForSale();

    InsuranceGuaranteeDto getInsuranceGuaranteeById(int insuranceId);

    InsurancePremiumDto inquireHealthPremium(int insuranceId, InquireHealthPremiumDto requestDto);

    InsurancePremiumDto inquireFirePremium(int insuranceId, InquireFirePremiumDto requestDto);

    InsurancePremiumDto inquireCarPremium(int insuranceId, InquireCarPremiumDto requestDto);

    List<InsuranceOfDeveloperDto> getInsuranceListOfDeveloper();

    InsurancePremiumDto calculateHealthPremium(CalculateHealthPremiumDto calculateHealthPremiumDto);

    InsurancePremiumDto calculateCarPremium(CalculateCarPremiumDto calculateCarPremiumDto);

    InsurancePremiumDto calculateFirePremium(CalculateFirePremiumDto calculateFirePremiumDto);

    InsuranceForUploadAuthFileDto getInsuranceInfoForUploadAuthFile(int insuranceId);

}
