package com.mju.insuranceCompany.application.viewlogic.dto.contract.dto;

import com.mju.insuranceCompany.application.domain.contract.CarType;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CarContractDto extends ContractDto {

    private String carNo;
    private CarType carType;
    private String modelName;
    private int modelYear;
    private Long value;

}
