package com.mju.insuranceCompany.service.contract.controller.dto;

import com.mju.insuranceCompany.service.contract.domain.CarContract;
import com.mju.insuranceCompany.service.contract.domain.CarType;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CarContractDto extends ContractDto {
    private int contractId;
    private String carNo;
    private CarType carType;
    private String modelName;
    private int modelYear;
    private Long value;

    public static CarContractDto toDtoFromEntity(CarContract carContract) {
        return new CarContractDto(
                carContract.getId(),
                carContract.getCarNo(),
                carContract.getCarType(),
                carContract.getModelName(),
                carContract.getModelYear(),
                carContract.getValue()
        );
    }
}
