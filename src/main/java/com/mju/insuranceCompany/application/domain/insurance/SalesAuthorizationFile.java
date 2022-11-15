package com.mju.insuranceCompany.application.domain.insurance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:39:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SalesAuthorizationFile {

	@Id
	private int insuranceId;

	/**
	 * 보험상품신고서
	 */
	private String prodDeclaration;
	private LocalDateTime modifiedProd;

	/**
	 * 보험요율산출기관 검증확인서
	 */
	private String isoVerification;
	private LocalDateTime modifiedIso;

	/**
	 * 선임계리사 검증기초서류
	 */
	private String srActuaryVerification;
	private LocalDateTime modifiedSrActuary;

	/**
	 * 금융감독원 인가허가파일
	 */
	private String fssOfficialDoc;
	private LocalDateTime modifiedFss;


	public String print() {
		String print = "{";
		if(prodDeclaration != null) print += "보험상품신고서(" + modifiedProd + "), ";
		if(isoVerification != null) print += "보험요율산출기관 검증확인서(" + modifiedIso + "), ";
		if(srActuaryVerification != null) print += "선임계리사 검증기초서류(" + modifiedSrActuary + "), ";
		if(fssOfficialDoc != null) print += "금융감독원 인가허가파일(" + modifiedFss + ")";
		print += "} ";
		return print;
	}

}