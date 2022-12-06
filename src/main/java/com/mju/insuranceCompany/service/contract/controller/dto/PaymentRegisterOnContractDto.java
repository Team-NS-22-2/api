package com.mju.insuranceCompany.service.contract.controller.dto;

import lombok.Data;

/*
    NAME : 결제수단 등록하기
    API : [PATCH] /customer/payment
 */
@Data
public class PaymentRegisterOnContractDto {
    private int contractId;
    private int paymentId;
}
