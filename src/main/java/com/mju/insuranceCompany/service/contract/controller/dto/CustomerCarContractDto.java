package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.customer.controller.dto.CustomerDto;
import com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceBasicInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerCarContractDto {
    private CustomerDto customerDto;
    private CarContractDto carContractDto;
    private InsuranceBasicInfoDto insuranceDto;
}
