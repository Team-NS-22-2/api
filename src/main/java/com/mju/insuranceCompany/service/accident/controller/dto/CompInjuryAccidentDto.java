package com.mju.insuranceCompany.service.accident.controller.dto;

import com.mju.insuranceCompany.service.accident.domain.InjuryAccident;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccidentDocumentFile;
import com.mju.insuranceCompany.service.customer.controller.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class CompInjuryAccidentDto {
    private LocalDateTime dateOfAccident;
    private LocalDateTime dateOfReport;
    private int accidentId;
    private String injurySite;
    private long lossReserves;
    private CustomerDto customerDto;
    private Map<String, String> fileUrlMap;

    public static CompInjuryAccidentDto toDto(InjuryAccident accident, CustomerDto customerDto, List<AccidentDocumentFile> fileList) {
        Map<String, String> fileMap = getFileMap(fileList);
        return new CompInjuryAccidentDto(
                accident.getDateOfAccident(),
                accident.getDateOfReport(),
                accident.getId(),
                accident.getInjurySite(),
                accident.getLossReserves(),
                customerDto,
                fileMap
        );
    }

    private static Map<String, String> getFileMap(List<AccidentDocumentFile> fileList) {
        if(fileList == null) return null;
        Map<String, String> fileMap = null;
        if(!fileList.isEmpty()) {
            fileMap = new HashMap<>();
            for (AccidentDocumentFile file : fileList) {
                switch (file.getType()) {
                    case CLAIM_COMP -> fileMap.put(AccDocType.CLAIM_COMP.getName(), file.getFileAddress());
                    case MEDICAL_CERTIFICATION -> fileMap.put(AccDocType.MEDICAL_CERTIFICATION.getName(), file.getFileAddress());
                    case CONFIRM_ADMISSION_DISCHARGE -> fileMap.put(AccDocType.CONFIRM_ADMISSION_DISCHARGE.getName(), file.getFileAddress());
                    case INVESTIGATE_ACCIDENT -> fileMap.put(AccDocType.INVESTIGATE_ACCIDENT.getName(), file.getFileAddress());
                }
            }
        }
        return fileMap;
    }

}
