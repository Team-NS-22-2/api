package com.mju.insuranceCompany.service.insurance.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class StandardPremiumDto {
    /**
     * 발생 손해액
     */
    private long damageAmount;
    /**
     * 계약건수
     */
    private long countContract;
    /**
     * 사업비
     */
    private long businessExpense;
    /**
     * 이익률
     */
    private double profitMargin;
}
