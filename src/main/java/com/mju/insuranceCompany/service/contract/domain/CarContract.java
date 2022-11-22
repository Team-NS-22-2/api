package com.mju.insuranceCompany.service.contract.domain;


import com.mju.insuranceCompany.service.contract.controller.dto.CarContractDto;
import lombok.*;

import javax.persistence.Entity;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:23
 */
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CarContract extends Contract{

	private String carNo;
	private CarType carType;
	private String modelName;
	private int modelYear;
	private Long value;

	public CarContract(CarContractDto dto, int customerId) {
		super(false,  dto.getPremium(), dto.getInsuranceId(), ConditionOfUw.WAIT, customerId, dto.getEmployeeId());
		this.carNo = dto.getCarNo();
		this.carType = dto.getCarType();
		modelName = dto.getModelName();
		modelYear = dto.getModelYear();
		value = dto.getValue();
	}

}