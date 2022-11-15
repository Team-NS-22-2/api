package com.mju.insuranceCompany.application.viewlogic.dto.contract;

import lombok.*;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HealthContractDto extends ContractDto {
    private int height;
    private int weight;
    @Column(columnDefinition = "TINYINT", length=1)
    private boolean isDrinking;
    @Column(columnDefinition = "TINYINT", length=1)
    private boolean isSmoking;
    @Column(columnDefinition = "TINYINT", length=1)
    private boolean isDriving;
    @Column(columnDefinition = "TINYINT", length=1)
    private boolean isDangerActivity;
    @Column(columnDefinition = "TINYINT", length=1)
    private boolean isHavingDisease;
    @Column(columnDefinition = "TINYINT", length=1)
    private boolean isTakingDrug;
    private String diseaseDetail;
}
