package com.mju.insuranceCompany.service.insurance.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:38:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class DevelopInfo {

	@Id
	private int insuranceId;
	private int employeeId;
	private LocalDate developDate;
	@Enumerated(value = EnumType.STRING)
	private SalesAuthorizationState salesAuthorizationState;
	private LocalDate salesStartDate;

}