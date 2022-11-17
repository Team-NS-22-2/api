package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.contract.domain.CarType;
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
