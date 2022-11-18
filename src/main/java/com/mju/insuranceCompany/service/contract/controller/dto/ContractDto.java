package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.contract.domain.ConditionOfUw;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ContractDto {
    private int insuranceId;
    private int customerId;
    private int premium;
    private ConditionOfUw conditionOfUw;
    private int employeeId;
}
