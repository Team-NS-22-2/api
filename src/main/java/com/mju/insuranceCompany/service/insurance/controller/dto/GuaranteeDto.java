package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuaranteeDto {
    private String name;
    private String description;
    private Long amount;
}
