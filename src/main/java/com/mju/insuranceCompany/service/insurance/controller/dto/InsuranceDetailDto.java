package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class InsuranceDetailDto {
    private int premium;
}
