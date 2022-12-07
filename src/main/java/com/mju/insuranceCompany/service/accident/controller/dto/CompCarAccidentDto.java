package com.mju.insuranceCompany.service.accident.controller.dto;

import com.mju.insuranceCompany.service.accident.domain.CarAccident;
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
public class CompCarAccidentDto {
    private LocalDateTime dateOfAccident;
    private LocalDateTime dateOfReport;
    private int accidentId;
    private String placeAddress;
    private String carNo;
    private String opposingDriverPhone;
    private boolean isRequestOnSite;
    private int errorRate;
    private long lossReserves;
    private CustomerDto customerDto;
    private Map<String, String> fileUrlMap;

    public static CompCarAccidentDto toDto(CarAccident accident, CustomerDto customerDto, List<AccidentDocumentFile> fileList) {
        Map<String, String> fileMap = getFileMap(fileList);
        return new CompCarAccidentDto(
                accident.getDateOfAccident(),
                accident.getDateOfReport(),
                accident.getId(),
                accident.getPlaceAddress(),
                accident.getCarNo(),
                accident.getOpposingDriverPhone(),
                accident.isRequestOnSite(),
                accident.getErrorRate(),
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
                    case PICTURE_OF_SITE -> fileMap.put(AccDocType.PICTURE_OF_SITE.getName(), file.getFileAddress());
                    case CAR_ACCIDENT_FACT_CONFIRMATION -> fileMap.put(AccDocType.CAR_ACCIDENT_FACT_CONFIRMATION.getName(), file.getFileAddress());
                    case PAYMENT_RESOLUTION -> fileMap.put(AccDocType.PAYMENT_RESOLUTION.getName(), file.getFileAddress());
                    case INVESTIGATE_ACCIDENT -> fileMap.put(AccDocType.INVESTIGATE_ACCIDENT.getName(), file.getFileAddress());
                    case LOSS_ASSESSMENT -> fileMap.put(AccDocType.LOSS_ASSESSMENT.getName(), file.getFileAddress());
                }
            }
        }
        return fileMap;
    }


}
