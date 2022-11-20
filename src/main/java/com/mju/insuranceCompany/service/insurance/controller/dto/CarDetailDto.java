package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class CarDetailDto extends InsuranceDetailDto {
    private int targetAge;
    private long valueCriterion;
}
