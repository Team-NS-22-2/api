package com.mju.insuranceCompany.service.accident.controller.dto;

import com.mju.outerSystem.AccidentWorker;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccidentWorkerDto {
    private String name;
    private String phoneNum;

    public static AccidentWorkerDto toDto(AccidentWorker accidentWorker) {
        return new AccidentWorkerDto(
                accidentWorker.getName(),
                accidentWorker.getPhoneNum()
        );
    }
}
