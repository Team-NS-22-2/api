package com.mju.insuranceCompany.service.customer.controller.dto;

import com.mju.insuranceCompany.service.customer.domain.payment.Account;
import com.mju.insuranceCompany.service.customer.domain.payment.Card;
import com.mju.insuranceCompany.service.customer.domain.payment.PayType;
import com.mju.insuranceCompany.service.customer.domain.payment.Payment;
import lombok.Data;

@Data
public class PaymentBasicInfoDto {

    private int paymentId;
    private PayType payType;
    private String no;

    public static PaymentBasicInfoDto toDto(Payment payment) {
        PaymentBasicInfoDto dto = new PaymentBasicInfoDto();
        dto.setPaymentId(payment.getId());
        dto.setPayType(payment.getPayType());
        if (payment.getPayType().equals(PayType.CARD)) {
            dto.setNo(((Card) payment).getCardNo());
        } else {
            dto.setNo(((Account) payment).getAccountNo());
        }
        return dto;
    }
}
