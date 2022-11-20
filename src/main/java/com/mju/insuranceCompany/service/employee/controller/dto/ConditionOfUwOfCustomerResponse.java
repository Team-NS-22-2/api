package com.mju.insuranceCompany.service.employee.controller.dto;

import com.mju.insuranceCompany.service.contract.domain.ConditionOfUw;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConditionOfUwOfCustomerResponse {
    private int customerId;
    private String customerName;
    private ConditionOfUw conditionOfUw;
}
