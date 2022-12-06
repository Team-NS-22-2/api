package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.contract.domain.ConditionOfUw;
import lombok.*;
import lombok.experimental.Accessors;

@Setter @Getter @ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ContractDto {
    protected int insuranceId;
    protected int customerId;
    protected int premium;
    protected ConditionOfUw conditionOfUw;
    protected int employeeId;
}
