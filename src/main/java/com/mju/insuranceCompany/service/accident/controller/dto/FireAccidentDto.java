package com.mju.insuranceCompany.service.accident.controller.dto;

import com.mju.insuranceCompany.service.accident.domain.FireAccident;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccidentDocumentFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class FireAccidentDto {
    private LocalDateTime dateOfAccident;
    private LocalDateTime dateOfReport;
    private int accidentId;
    private String placeAddress;
    private Map<String, String> fileUrlMap;

    public static FireAccidentDto toDto(FireAccident accident, List<AccidentDocumentFile> fileList) {
        Map<String, String> fileMap = getFileMap(fileList);
        return new FireAccidentDto(
                accident.getDateOfAccident(),
                accident.getDateOfReport(),
                accident.getId(),
                accident.getPlaceAddress(),
                fileMap
        );
    }

    private static Map<String, String> getFileMap(List<AccidentDocumentFile> fileList) {
        Map<String, String> fileMap = null;
        if(!fileList.isEmpty()) {
            fileMap = new HashMap<>();
            for (AccidentDocumentFile file : fileList) {
                switch (file.getType()) {
                    case CLAIM_COMP -> fileMap.put(AccDocType.CLAIM_COMP.getName(), file.getFileAddress());
                    case PICTURE_OF_SITE -> fileMap.put(AccDocType.PICTURE_OF_SITE.getName(), file.getFileAddress());
                    case REPAIR_ESTIMATE -> fileMap.put(AccDocType.REPAIR_ESTIMATE.getName(), file.getFileAddress());
                    case REPAIR_RECEIPT -> fileMap.put(AccDocType.REPAIR_RECEIPT.getName(), file.getFileAddress());
                }
            }
        }
        return fileMap;
    }

}
