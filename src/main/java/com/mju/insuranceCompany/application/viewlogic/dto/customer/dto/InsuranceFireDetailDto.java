package com.mju.insuranceCompany.application.viewlogic.dto.customer.dto;

import com.mju.insuranceCompany.application.domain.insurance.InsuranceType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InsuranceFireDetailDto {
    private int id;
    private InsuranceType insuranceType;
    private List<FireDetailDto> insuranceDetailList;
}
