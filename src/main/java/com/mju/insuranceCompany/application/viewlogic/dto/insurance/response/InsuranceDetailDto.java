package com.mju.insuranceCompany.application.viewlogic.dto.insurance.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InsuranceDetailDto {
    private int id;
    private int premium;
}
