package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaveHealthInsuranceDto {
    private InsuranceBasicInfoDto insuranceBasicInfoDto;
    private List<GuaranteeDto> guaranteeDtoList;
    private List<HealthDetailDto> healthDetailDtoList;
}
