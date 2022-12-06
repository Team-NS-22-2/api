package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.Data;

@Data
public class CalculateHealthPremiumDto {
    private StandardPremiumDto standardPremiumDto;
    private HealthDetailDto healthDetailDto;
}
