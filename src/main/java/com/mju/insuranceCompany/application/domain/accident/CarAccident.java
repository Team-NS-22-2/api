package com.mju.insuranceCompany.application.domain.accident;


import com.mju.insuranceCompany.application.global.constant.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.format.DateTimeFormatter;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CarAccident extends Accident {

	private String carNo;
	private int errorRate;
	@Column(columnDefinition = "TINYINT", length=1)
	private boolean isRequestOnSite; // 현장 출동 요청
	private String opposingDriverPhone;
	private String placeAddress;

}