package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.customer.controller.dto.CustomerDto;
import com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceBasicInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerHealthContractDto {
    private CustomerDto customerDto;
    private HealthContractDto healthContractDto;
    private InsuranceBasicInfoDto insuranceDto;
}
