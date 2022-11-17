package com.mju.insuranceCompany.service.customer.controller.dto;

import com.mju.insuranceCompany.service.contract.controller.dto.FireContractDto;
import lombok.Data;

@Data
public class RegisterFireContractRequest {
    private CustomerBasicRequest customerDto;
    private FireContractDto fireContractDto;
}
