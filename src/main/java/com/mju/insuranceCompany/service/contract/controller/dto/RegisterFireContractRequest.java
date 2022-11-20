package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.contract.controller.dto.FireContractDto;
import com.mju.insuranceCompany.service.customer.controller.dto.CustomerBasicRequest;
import lombok.Data;

@Data
public class RegisterFireContractRequest {
    private CustomerBasicRequest customerDto;
    private FireContractDto fireContractDto;
}
