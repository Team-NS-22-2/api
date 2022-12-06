package com.mju.insuranceCompany.service.contract.service.interfaces;

import com.mju.insuranceCompany.service.contract.controller.dto.PaymentRegisterOnContractDto;

public interface PaymentRegisterService {

    void registerPaymentOnContract(PaymentRegisterOnContractDto dto);

}
