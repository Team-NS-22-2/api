package com.mju.insuranceCompany.application.viewlogic.dto.customer.request;

import com.mju.insuranceCompany.application.viewlogic.dto.contract.dto.FireContractDto;
import lombok.Data;

@Data
public class RegisterFireContractRequest {
    private CustomerBasicRequest customerDto;
    private FireContractDto fireContractDto;
}
