package com.mju.insuranceCompany.service.insurance.controller.dto;

import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InsuranceGuaranteeResponse {
    private String name;
    private InsuranceType type;
    private int contractPeriod;
    private int paymentPeriod;
    private List<GuaranteeDto> guaranteeList;
}
