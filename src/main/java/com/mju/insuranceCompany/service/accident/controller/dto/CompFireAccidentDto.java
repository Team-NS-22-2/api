package com.mju.insuranceCompany.service.accident.controller.dto;

import com.mju.insuranceCompany.service.accident.domain.FireAccident;
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
public class CompFireAccidentDto {
    private LocalDateTime dateOfAccident;
    private LocalDateTime dateOfReport;
    private int accidentId;
    private String placeAddress;
    private long lossReserves;
    private CustomerDto customerDto;
    private Map<String, String> fileUrlMap;

    public static CompFireAccidentDto toDto(FireAccident accident, CustomerDto customerDto, List<AccidentDocumentFile> fileList) {
        Map<String, String> fileMap = getFileMap(fileList);
        return new CompFireAccidentDto(
                accident.getDateOfAccident(),
                accident.getDateOfReport(),
                accident.getId(),
                accident.getPlaceAddress(),
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
                    case PICTURE_OF_SITE -> fileMap.put(AccDocType.PICTURE_OF_SITE.getName(), file.getFileAddress());
                    case REPAIR_ESTIMATE -> fileMap.put(AccDocType.REPAIR_ESTIMATE.getName(), file.getFileAddress());
                    case REPAIR_RECEIPT -> fileMap.put(AccDocType.REPAIR_RECEIPT.getName(), file.getFileAddress());
                    case INVESTIGATE_ACCIDENT -> fileMap.put(AccDocType.INVESTIGATE_ACCIDENT.getName(), file.getFileAddress());
                    case LOSS_ASSESSMENT -> fileMap.put(AccDocType.LOSS_ASSESSMENT.getName(), file.getFileAddress());
                }
            }
        }
        return fileMap;
    }

}
