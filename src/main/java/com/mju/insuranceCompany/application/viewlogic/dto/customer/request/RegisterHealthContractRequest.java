package com.mju.insuranceCompany.application.viewlogic.dto.customer.request;

import com.mju.insuranceCompany.application.viewlogic.dto.contract.HealthContractDto;
import lombok.Data;

@Data
public class RegisterHealthContractRequest {
    private CustomerBasicRequest customerDto;
    private HealthContractDto healthContractDto;
}
