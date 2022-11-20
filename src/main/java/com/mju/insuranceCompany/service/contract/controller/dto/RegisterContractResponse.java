package com.mju.insuranceCompany.service.contract.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterContractResponse {
    private int customerId;
}
