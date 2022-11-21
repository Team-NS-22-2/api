package com.mju.insuranceCompany.service.employee.controller;

import com.mju.insuranceCompany.service.contract.domain.ConditionOfUw;
import lombok.Data;

@Data
public class UnderwritingRequest {
    private String reasonOfUw;
    private ConditionOfUw conditionOfUw;
}
