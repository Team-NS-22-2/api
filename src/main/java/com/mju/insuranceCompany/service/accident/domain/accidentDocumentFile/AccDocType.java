package com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile;

import com.mju.insuranceCompany.service.accident.exception.AccidentDocumentTypeBadRequestException;

/**
 * packageName :  domain.accident.accDocFile
 * fileName : AccDocType
 * author :  규현
 * date : 2022-05-20
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-20                규현             최초 생성
 */
public enum AccDocType {
    CLAIM_COMP("claim_comp", "보험금 청구 서류"),
    MEDICAL_CERTIFICATION("medical_certification", "진단서"),
    CONFIRM_ADMISSION_DISCHARGE("confirm_admission_discharge", "입퇴원 확인서"),
    PICTURE_OF_SITE("picture_of_site", "사고현장 사진"),
    REPAIR_ESTIMATE("repair_estimate", "수리비 견적서"),
    REPAIR_RECEIPT("repair_receipt", "수리비 영수증"),
    CAR_ACCIDENT_FACT_CONFIRMATION("car_accident_fact_confirmation", "교통사고 사실확인원"),
    PAYMENT_RESOLUTION("payment_resolution", "자동차보험금 지급결의서"),
    INVESTIGATE_ACCIDENT("investigate_accident", "사고조사 보고서"),
    LOSS_ASSESSMENT("loss_assessment", "손해사정서");


    private String name;
    private String desc;
    AccDocType(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public static AccDocType ofType(String source) {
        for(AccDocType type : AccDocType.values()) {
            if(type.name.equals(source)) {
                return type;
            }
        }
        throw new AccidentDocumentTypeBadRequestException();
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return desc;
    }

}
