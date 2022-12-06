package com.mju.insuranceCompany.service.insurance.controller.dto;

import com.mju.insuranceCompany.service.insurance.domain.Guarantee;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuaranteeDto {
    private String name;
    private String description;
    private Long amount;

    public static GuaranteeDto toDto(Guarantee guarantee) {
        return GuaranteeDto.builder()
                .name(guarantee.getName())
                .description(guarantee.getDescription())
                .amount(guarantee.getGuaranteeAmount())
                .build();
    }
}
