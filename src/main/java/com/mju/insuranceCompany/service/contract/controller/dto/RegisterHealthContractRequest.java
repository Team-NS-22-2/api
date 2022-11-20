package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.contract.controller.dto.HealthContractDto;
import com.mju.insuranceCompany.service.customer.controller.dto.CustomerBasicRequest;
import lombok.Data;

@Data
public class RegisterHealthContractRequest {
    private CustomerBasicRequest customerDto;
    private HealthContractDto healthContractDto;
}
