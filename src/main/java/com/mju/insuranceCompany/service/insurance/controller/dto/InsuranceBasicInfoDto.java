package com.mju.insuranceCompany.service.insurance.controller.dto;

import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InsuranceBasicInfoDto {
    private String name;
    private String description;
    private int contractPeriod;
    private int paymentPeriod;

    public static InsuranceBasicInfoDto toDto(Insurance insurance) {
        return new InsuranceBasicInfoDto(
                insurance.getName(),
                insurance.getDescription(),
                insurance.getContractPeriod(),
                insurance.getPaymentPeriod()
        );
    }
}
