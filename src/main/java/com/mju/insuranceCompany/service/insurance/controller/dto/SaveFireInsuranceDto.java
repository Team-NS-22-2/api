package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaveFireInsuranceDto {
    private InsuranceBasicInfoDto insuranceBasicInfoDto;
    private List<GuaranteeDto> guaranteeDtoList;
    private List<FireDetailDto> fireDetailDtoList;
}
