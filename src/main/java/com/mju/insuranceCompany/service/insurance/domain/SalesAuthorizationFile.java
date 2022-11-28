package com.mju.insuranceCompany.service.insurance.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "insurance_id")
	private Insurance insurance;

	/**
	 * 보험상품신고서
	 */
	private String prodDeclaration;
	private LocalDateTime modifiedProd;
	public SalesAuthorizationFile setProdDeclaration(String url) {
		this.prodDeclaration = url;
		this.modifiedProd = LocalDateTime.now();
		return this;
	}
	public boolean isExistProd() {
		if (prodDeclaration != null) {
			return !prodDeclaration.isBlank();
		}
		return false;
	}

	/**
	 * 보험요율산출기관 검증확인서
	 */
	private String isoVerification;
	private LocalDateTime modifiedIso;
	public SalesAuthorizationFile setIsoVerification(String url) {
		this.isoVerification = url;
		this.modifiedIso = LocalDateTime.now();
		return this;
	}
	public boolean isExistIso() {
		if (isoVerification != null) {
			return !isoVerification.isBlank();
		}
		return false;
	}

	/**
	 * 선임계리사 검증기초서류
	 */
	private String srActuaryVerification;
	private LocalDateTime modifiedSrActuary;
	public SalesAuthorizationFile setSrActuaryVerification(String url) {
		this.srActuaryVerification = url;
		this.modifiedSrActuary = LocalDateTime.now();
		return this;
	}
	public boolean isExistSrActuary() {
		if (srActuaryVerification != null) {
			return !srActuaryVerification.isBlank();
		}
		return false;
	}

	/**
	 * 금융감독원 인가허가파일
	 */
	private String fssOfficialDoc;
	private LocalDateTime modifiedFss;
	public SalesAuthorizationFile setFssOfficialDoc(String url) {
		this.fssOfficialDoc = url;
		this.modifiedFss = LocalDateTime.now();
		return this;
	}
	public boolean isExistFssOfficial() {
		if (fssOfficialDoc != null) {
			return !fssOfficialDoc.isBlank();
		}
		return false;
	}

	/**
	 * 모든 파일이 존재하는지 확인하는 메소드
	 * @return 모든 파일이 있으면 true, 하나의 파일이라도 없으면 false
	 */
	public boolean isExistAllFile() {
		return isExistProd() && isExistIso() && isExistSrActuary() && isExistFssOfficial();
	}

}