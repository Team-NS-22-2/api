package com.mju.insuranceCompany.application.viewlogic.dto.customer.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class InsuranceDetailDto {
    private int premium;
}
