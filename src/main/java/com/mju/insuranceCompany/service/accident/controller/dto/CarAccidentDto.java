package com.mju.insuranceCompany.service.accident.controller.dto;

import com.mju.insuranceCompany.service.accident.domain.CarAccident;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CarAccidentDto {
    private LocalDateTime dateOfAccident;
    private LocalDateTime dateOfReport;
    private int accidentId;
    private String placeAddress;
    private String carNo;
    private String opposingDriverPhone;
    private Boolean isRequestOnSite;
    private AccidentWorkerDto accidentWorker;
    // 이하 각 파일의 주소

    public static CarAccidentDto toDto(CarAccident accident, AccidentWorkerDto accidentWorkerDto){
        return new CarAccidentDto(
                accident.getDateOfAccident(),
                accident.getDateOfReport(),
                accident.getId(),
                accident.getPlaceAddress(),
                accident.getCarNo(),
                accident.getOpposingDriverPhone(),
                accident.isRequestOnSite(),
                accidentWorkerDto
        );
    }
}
