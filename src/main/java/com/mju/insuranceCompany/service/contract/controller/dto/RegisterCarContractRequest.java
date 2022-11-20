package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.customer.controller.dto.CustomerBasicRequest;
import lombok.Data;

@Data
public class RegisterCarContractRequest {
    private CustomerBasicRequest customerDto;
    private CarContractDto carContractDto;
}
