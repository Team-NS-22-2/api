package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StandardPremiumDto {
    private long damageAmount;
    private long countContract;
    private long businessExpense;
    private double profitMargin;
}
