package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.customer.controller.dto.CustomerBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerHealthContractDto {
    private CustomerBasicDto customerDto;
    private HealthContractDto healthContractDto;
}
