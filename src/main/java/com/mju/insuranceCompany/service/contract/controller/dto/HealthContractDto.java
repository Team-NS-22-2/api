package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.contract.domain.HealthContract;
import lombok.*;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HealthContractDto extends ContractDto {
    private int contractId;
    private int height;
    private int weight;
    private Boolean isDrinking;
    private Boolean isSmoking;
    private Boolean isDriving;
    private Boolean isDangerActivity;
    private Boolean isHavingDisease;
    private Boolean isTakingDrug;
    private String diseaseDetail;

    public static HealthContractDto toDtoFromEntity(HealthContract healthContract) {
        return new HealthContractDto(
                healthContract.getId(),
                healthContract.getHeight(),
                healthContract.getWeight(),
                healthContract.isDrinking(),
                healthContract.isSmoking(),
                healthContract.isDriving(),
                healthContract.isDangerActivity(),
                healthContract.isHavingDisease(),
                healthContract.isTakingDrug(),
                healthContract.getDiseaseDetail()
        );
    }
}
