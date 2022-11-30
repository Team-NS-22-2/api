package com.mju.insuranceCompany.service.accident.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccidentReportDto {
    // 공통 정보
    private LocalDateTime dateOfAccident;

    // 차량, 화재 공통
    private String placeAddress;

    //차량 공통
    private String carNo;

    // 차 사고
    private String opposingDriverPhone;
    private Boolean isRequestOnSite; // 현장 출동 요청

    // 차 고장
    private String symptom;

    //상해
    private String injurySite; // 부상 부위
}
