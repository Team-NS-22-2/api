package com.mju.insuranceCompany.service.contract.controller.dto;

import lombok.*;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HealthContractDto extends ContractDto {
    private int height;
    private int weight;
    private Boolean isDrinking;
    private Boolean isSmoking;
    private Boolean isDriving;
    private Boolean isDangerActivity;
    private Boolean isHavingDisease;
    private Boolean isTakingDrug;
    private String diseaseDetail;
}
