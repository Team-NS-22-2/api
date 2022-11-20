package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.customer.controller.dto.CustomerBasicDto;
import lombok.Data;

@Data
public class CustomerCarContractDto {
    private CustomerBasicDto customerDto;
    private CarContractDto carContractDto;
}
