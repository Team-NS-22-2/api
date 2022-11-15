package com.mju.insuranceCompany.application.domain.insurance;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@JoinColumn(name = "insurance_id")
	private int insuranceId;
	private int employeeId;
	private LocalDate developDate;
	private SalesAuthorizationState salesAuthorizationState;
	private LocalDate salesStartDate;

	public String print() {
		String value =
				"{개발직원ID: " + employeeId +
				", 개발일자: " + developDate.getYear() + "년 " +
							developDate.getMonthValue() + "월 "+
							developDate.getDayOfMonth() + "일" +
				", 판매인가상태: " + salesAuthorizationState.name();
		if(salesAuthorizationState == SalesAuthorizationState.PERMISSION) {
			value += ", 판매시작일자: " + salesStartDate.getYear() + "년 " +
									salesStartDate.getMonthValue() + "월 " +
									salesStartDate.getDayOfMonth() + "일";
		}
		value += "}";
		return value;
	}
}