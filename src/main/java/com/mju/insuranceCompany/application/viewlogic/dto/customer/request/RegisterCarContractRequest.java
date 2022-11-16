package com.mju.insuranceCompany.application.viewlogic.dto.customer.request;

import com.mju.insuranceCompany.application.viewlogic.dto.contract.dto.CarContractDto;
import lombok.Data;

@Data
public class RegisterCarContractRequest {
    private CustomerBasicRequest customerDto;
    private CarContractDto carContractDto;
}
