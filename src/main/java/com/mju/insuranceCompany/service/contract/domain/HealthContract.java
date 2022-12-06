package com.mju.insuranceCompany.service.contract.domain;


import com.mju.insuranceCompany.service.contract.controller.dto.HealthContractDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:24
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
public class HealthContract extends Contract {

    private int height;
    private int weight;
    private boolean isDrinking;
    private boolean isSmoking;
    private boolean isDriving;
    private boolean isDangerActivity;
    private boolean isHavingDisease;
    private boolean isTakingDrug;
    private String diseaseDetail;

    public HealthContract(HealthContractDto dto, int customerId) {
        super(false,  dto.getPremium(), dto.getInsuranceId(), ConditionOfUw.WAIT, customerId, dto.getEmployeeId());
        this.height = dto.getHeight();
        this.weight = dto.getWeight();
        this.isDrinking = dto.getIsDrinking();
        this.isSmoking = dto.getIsSmoking();
        this.isDriving = dto.getIsDriving();
        this.isDangerActivity = dto.getIsDangerActivity();
        this.isHavingDisease = dto.getIsHavingDisease();
        this.isTakingDrug = dto.getIsTakingDrug();
        this.diseaseDetail = dto.getDiseaseDetail();
    }

}