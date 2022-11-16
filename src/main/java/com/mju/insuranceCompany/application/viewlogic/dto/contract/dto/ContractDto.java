package com.mju.insuranceCompany.application.viewlogic.dto.contract.dto;

import com.mju.insuranceCompany.application.domain.contract.ConditionOfUw;
import lombok.*;
import lombok.experimental.Accessors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Accessors(chain = true)
public class ContractDto {
    private int insuranceId;
    private int customerId;
    private int premium;
    private ConditionOfUw conditionOfUw;
}
