package com.mju.insuranceCompany.service.insurance.controller.dto;


import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsuranceListDto {
    private int id;
    private String name;
    private InsuranceType type;
}
