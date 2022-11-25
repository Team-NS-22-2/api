package com.mju.insuranceCompany.service.insurance.controller.dto;

import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InsuranceGuaranteeDto {
    private String name;
    private InsuranceType type;
    private int contractPeriod;
    private int paymentPeriod;
    private List<GuaranteeDto> guaranteeList;

    public static InsuranceGuaranteeDto toDto(Insurance insurance) {
        return InsuranceGuaranteeDto.builder()
                .name(insurance.getName())
                .type(insurance.getInsuranceType())
                .contractPeriod(insurance.getContractPeriod())
                .paymentPeriod(insurance.getPaymentPeriod())
                .guaranteeList(insurance.getGuaranteeList().stream().map(GuaranteeDto::toDto).toList())
                .build();
    }
}
