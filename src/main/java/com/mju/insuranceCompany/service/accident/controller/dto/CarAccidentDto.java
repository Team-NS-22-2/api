package com.mju.insuranceCompany.service.accident.controller.dto;

import com.mju.insuranceCompany.service.accident.domain.CarAccident;
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
    private Map<String, String> fileUrlMap;

    public static CarAccidentDto toDto(CarAccident accident, AccidentWorkerDto accidentWorkerDto, List<AccidentDocumentFile> fileList){
        Map<String, String> fileMap = getFileMap(fileList);
        return new CarAccidentDto(
                accident.getDateOfAccident(),
                accident.getDateOfReport(),
                accident.getId(),
                accident.getPlaceAddress(),
                accident.getCarNo(),
                accident.getOpposingDriverPhone(),
                accident.isRequestOnSite(),
                accidentWorkerDto,
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
                }
            }
        }
        return fileMap;
    }

}
