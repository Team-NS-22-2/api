package com.mju.insuranceCompany.service.insurance.applicationservice.interfaces;

import com.mju.insuranceCompany.service.insurance.controller.dto.SaveCarInsuranceDto;
import com.mju.insuranceCompany.service.insurance.controller.dto.SaveFireInsuranceDto;
import com.mju.insuranceCompany.service.insurance.controller.dto.SaveHealthInsuranceDto;

public interface InsuranceCreateService {

    void saveHealthInsurance(SaveHealthInsuranceDto saveHealthInsuranceDto);

    void saveCarInsurance(SaveCarInsuranceDto saveCarInsuranceDto);

    void saveFireInsurance(SaveFireInsuranceDto saveFireInsuranceDto);



}
