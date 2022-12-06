package com.mju.insuranceCompany.service.accident.controller.dto;

import com.mju.insuranceCompany.service.accident.domain.Accident;
import com.mju.insuranceCompany.service.accident.domain.AccidentType;
import com.mju.insuranceCompany.service.accident.domain.CompState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class AccidentListInfoDto {
    private int accidentId;
    private AccidentType accidentType;
    private LocalDateTime dateOfAccident;
    private LocalDateTime dateOfReport;
    private CompEmployeeDto compEmployee;
    private CompState compState;

    public static AccidentListInfoDto toDto(Accident accident) {
        return AccidentListInfoDto.builder()
                .accidentId(accident.getId())
                .accidentType(accident.getAccidentType())
                .dateOfAccident(accident.getDateOfAccident())
                .dateOfReport(accident.getDateOfReport())
                .compState(accident.getCompState())
                .build();
    }

    public static AccidentListInfoDto toDto(Accident accident, CompEmployeeDto compEmployee) {
        return new AccidentListInfoDto(
                accident.getId(),
                accident.getAccidentType(),
                accident.getDateOfAccident(),
                accident.getDateOfReport(),
                compEmployee,
                accident.getCompState()
        );
    }
}
