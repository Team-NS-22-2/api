package com.mju.insuranceCompany.service.insurance.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
	@MapsId
	@OneToOne
	@JoinColumn(name = "insurance_id")
	private Insurance insurance;
	private int employeeId;
	private LocalDate developDate;
	@Enumerated(value = EnumType.STRING)
	private SalesAuthorizationState salesAuthorizationState;
	private LocalDate salesStartDate;

}