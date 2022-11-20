package com.mju.insuranceCompany.service.insurance.controller.dto;

import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InsuranceCarDetailDto {
    private int id;
    private InsuranceType insuranceType;
    private List<CarDetailDto> insuranceDetailList;
}
