package com.mju.insuranceCompany.service.accident.applicationservice.interfaces;

import com.mju.insuranceCompany.service.accident.domain.AccidentType;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccDocType;
import org.springframework.web.multipart.MultipartFile;

public interface AccidentFileService {

    /**
     * 고객의 사고 관련 파일을 S3에 업로드하는 메소드.
     * @param accidentId 업로드하는 파일의 사고 ID
     * @param docType 업로드하는 파일의 타입
     * @param multipartFile 업로드하는 파일
     * @param accidentType 업로드하는 파일의 사고의 타입
     */
    void submitAccDocFileByCustomer(int accidentId, AccDocType docType, MultipartFile multipartFile, AccidentType accidentType);

    /** 보상팀 직원의 사고조사 보고서 및 손해 사정서 제출 */
    void submitAccDocFileByCompEmployee(int accidentId, MultipartFile multipartFile, AccDocType docType);

}
