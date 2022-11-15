package com.mju.insuranceCompany.application.domain.accident;


import com.mju.insuranceCompany.application.global.constant.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class FireAccident extends Accident {

	private String placeAddress;

}