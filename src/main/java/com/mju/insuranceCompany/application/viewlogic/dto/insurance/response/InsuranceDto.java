package com.mju.insuranceCompany.application.viewlogic.dto.insurance.response;

import com.mju.insuranceCompany.application.domain.insurance.InsuranceType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InsuranceDto {
    private int id;
    private String name;
    private String description;
    private int contractPeriod;
    private int paymentPeriod;
    private InsuranceType insuranceType;
    private List<GuaranteeDto> guaranteeList;
    private List<InsuranceDetailDto> insuranceDetailList;
}
