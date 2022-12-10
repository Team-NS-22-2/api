package com.mju.insuranceCompany.service.customer.service.interfaces;

import com.mju.insuranceCompany.service.customer.controller.dto.PaymentCreateDto;

public interface CustomerUpdateService {

    void addNewPayment(PaymentCreateDto paymentCreateDto);

}
