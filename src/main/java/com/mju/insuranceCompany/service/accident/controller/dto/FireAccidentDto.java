package com.mju.insuranceCompany.service.accident.controller.dto;

import com.mju.insuranceCompany.service.accident.domain.FireAccident;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FireAccidentDto {
    private LocalDateTime dateOfAccident;
    private LocalDateTime dateOfReport;
    private int accidentId;
    private String placeAddress;

    public static FireAccidentDto toDto(FireAccident accident) {
        return new FireAccidentDto(
                accident.getDateOfAccident(),
                accident.getDateOfReport(),
                accident.getId(),
                accident.getPlaceAddress()
        );
    }
}
