package com.mju.insuranceCompany.application.viewlogic.dto.insurance.response;


import com.mju.insuranceCompany.application.domain.insurance.InsuranceType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsuranceListDto {
    private int id;
    private String name;
    private InsuranceType type;
}
