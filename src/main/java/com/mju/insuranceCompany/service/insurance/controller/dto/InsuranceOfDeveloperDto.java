package com.mju.insuranceCompany.service.insurance.controller.dto;

import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import com.mju.insuranceCompany.service.insurance.domain.SalesAuthorizationState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data @Builder
@AllArgsConstructor
public class InsuranceOfDeveloperDto {
    private int insuranceId;
    private InsuranceType insuranceType;
    private String insuranceName;
    private String description;
    private LocalDate developDate;
    private SalesAuthorizationState salesAuthorizationState;
    private LocalDate salesStartDate;

}
