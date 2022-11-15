package com.mju.insuranceCompany.application.viewlogic.dto.customer.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterContractResponse {
    private int customerId;
}
