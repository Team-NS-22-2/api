package com.mju.insuranceCompany.service.accident.controller.dto;

import com.mju.insuranceCompany.service.accident.domain.InjuryAccident;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InjuryAccidentDto {
    private LocalDateTime dateOfAccident;
    private LocalDateTime dateOfReport;
    private int accidentId;
    private String injurySite;

    public static InjuryAccidentDto toDto(InjuryAccident accident) {
        return new InjuryAccidentDto(
                accident.getDateOfAccident(),
                accident.getDateOfReport(),
                accident.getId(),
                accident.getInjurySite()
        );
    }
}
