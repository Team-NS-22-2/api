package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaveCarInsuranceDto {
    private InsuranceBasicInfoDto insuranceBasicInfoDto;
    private List<GuaranteeDto> guaranteeDtoList;
    private List<CarDetailDto> carDetailDtoList;
}
