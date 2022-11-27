package com.mju.insuranceCompany.service.customer.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
    name : 계약 목록 조회
    API : [GET] /customer/contract
    설명 : 결제를 위한 정보를 받는 DTO
          계약에 대한 결제 정보(이름, 값)이라서 Contract(계약) + Receipt(영수증)으로 명명
 */
@Data @AllArgsConstructor
public class ContractReceiptDto {

    private int contractId;
    private String insuranceName;
    private int premium;

}
