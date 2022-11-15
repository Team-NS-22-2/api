package com.mju.insuranceCompany.application.viewlogic.dto.insurance.response;

import com.mju.insuranceCompany.application.domain.insurance.InsuranceType;
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
}
