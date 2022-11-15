package com.mju.insuranceCompany.application.viewlogic.dto.contract;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HealthContractDto extends ContractDto {
    private int height;
    private int weight;
    private boolean isDrinking;
    private boolean isSmoking;
    private boolean isDriving;
    private boolean isDangerActivity;
    private boolean isHavingDisease;
    private boolean isTakingDrug;
    private String diseaseDetail;
}
