package com.mju.insuranceCompany.application.viewlogic.dto.insurance.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuaranteeDto {
    private String name;
    private String description;
    private Long amount;
}
