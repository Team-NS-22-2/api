package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.Data;

@Data
public class CalculateCarPremiumDto {
    private StandardPremiumDto standardPremiumDto;
    private CarDetailDto carDetailDto;
}
