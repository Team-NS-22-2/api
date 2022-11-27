package com.mju.insuranceCompany.service.customer.controller.dto;

import com.mju.insuranceCompany.service.customer.domain.payment.BankType;
import com.mju.insuranceCompany.service.customer.domain.payment.CardType;
import com.mju.insuranceCompany.service.customer.domain.payment.PayType;

import java.time.LocalDate;

/**
 * packageName :  main.domain.payment
 * fileName : PaymentDto
 * author :  규현
 * date : 2022-05-16
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-16                규현             최초 생성
 */
public class PaymentCreateDto {

    private PayType payType;
    private int customerId;

    //cardInfo
    private String cardNo;
    private CardType cardType;
    private String cvcNo;
    private LocalDate expiryDate;

    //accountInfo
    private String accountNo;
    private BankType bankType;

    public PayType getPayType() {
        return payType;
    }

    public PaymentCreateDto setPayType(PayType payType) {
        this.payType = payType;
        return this;
    }

    public int getCustomerId() {
        return customerId;
    }

    public PaymentCreateDto setCustomerId(int customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCardNo() {
        return cardNo;
    }

    public PaymentCreateDto setCardNo(String cardNo) {
        this.cardNo = cardNo;
        return this;
    }

    public CardType getCardType() {
        return cardType;
    }

    public PaymentCreateDto setCardType(CardType cardType) {
        this.cardType = cardType;
        return this;
    }

    public String getCvcNo() {
        return cvcNo;
    }

    public PaymentCreateDto setCvcNo(String cvcNo) {
        this.cvcNo = cvcNo;
        return this;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public PaymentCreateDto setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public PaymentCreateDto setAccountNo(String accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public BankType getBankType() {
        return bankType;
    }

    public PaymentCreateDto setBankType(BankType bankType) {
        this.bankType = bankType;
        return this;
    }
}
