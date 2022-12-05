package com.mju.insuranceCompany.service.accident.controller.dto;

import lombok.Data;

@Data
public class PaymentOfCompensationDto {
    private String bank;
    private String accountNo;
    private long amount;
}
