package com.mju.insuranceCompany.service.accident.controller.dto;

import com.mju.insuranceCompany.service.accident.domain.CarBreakdown;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CarBreakdownDto {
    private LocalDateTime dateOfAccident;
    private LocalDateTime dateOfReport;
    private int accidentId;
    private String placeAddress;
    private String carNo;
    private AccidentWorkerDto accidentWorker;

    public static CarBreakdownDto toDto(CarBreakdown accident, AccidentWorkerDto accidentWorkerDto) {
        return new CarBreakdownDto(
                accident.getDateOfAccident(),
                accident.getDateOfReport(),
                accident.getId(),
                accident.getPlaceAddress(),
                accident.getCarNo(),
                accidentWorkerDto
        );
    }
}
