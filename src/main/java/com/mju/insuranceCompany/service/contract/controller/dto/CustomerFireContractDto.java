package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.customer.controller.dto.CustomerBasicDto;
import lombok.Data;

@Data
public class CustomerFireContractDto {
    private CustomerBasicDto customerDto;
    private FireContractDto fireContractDto;
}
