package com.mju.insuranceCompany.service.insurance.controller.dto;

import com.mju.insuranceCompany.service.insurance.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class InsuranceForUploadAuthFileDto {
    private int id;
    private InsuranceType type;
    private String name;
    private String description;
    private LocalDate developDate;
    private SalesAuthorizationState salesAuthorizationState;
    private Boolean isExistProd;
    private Boolean isExistIso;
    private Boolean isExistSrActuary;
    private Boolean isExistFssOfficial;

    public static InsuranceForUploadAuthFileDto toDto(Insurance insurance) {
        DevelopInfo developInfo = insurance.getDevelopInfo();
        SalesAuthorizationFile salesAuthorizationFile = insurance.getSalesAuthorizationFile();
        return new InsuranceForUploadAuthFileDto(
                insurance.getId(),
                insurance.getInsuranceType(),
                insurance.getName(),
                insurance.getDescription(),
                developInfo.getDevelopDate(),
                developInfo.getSalesAuthorizationState(),
                salesAuthorizationFile.isExistProd(),
                salesAuthorizationFile.isExistIso(),
                salesAuthorizationFile.isExistSrActuary(),
                salesAuthorizationFile.isExistFssOfficial()
        );
    }
}
