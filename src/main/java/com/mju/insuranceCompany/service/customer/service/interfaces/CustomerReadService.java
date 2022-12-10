package com.mju.insuranceCompany.service.customer.service.interfaces;

import com.mju.insuranceCompany.service.customer.controller.dto.PaymentBasicInfoDto;

import java.util.List;

public interface CustomerReadService {

    List<PaymentBasicInfoDto> getAllPaymentInfos();

}
