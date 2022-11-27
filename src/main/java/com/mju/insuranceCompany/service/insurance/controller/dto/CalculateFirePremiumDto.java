package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.Data;

@Data
public class CalculateFirePremiumDto {
    private StandardPremiumDto standardPremiumDto;
    private FireDetailDto fireDetailDto;
}
