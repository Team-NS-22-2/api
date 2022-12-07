package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter @RequiredArgsConstructor
public enum AccidentErrorCode implements ErrorCode {

    CANNOT_REPORT_CAR_ACCIDENT(HttpStatus.BAD_REQUEST, "고객님은 가입된 자동차보험이 없어 자동차사고를 접수하실 수 없습니다."),
    NOT_EXIST_REQUESTED_CAR_NO(HttpStatus.BAD_REQUEST, "해당 차량 번호로 가입되어 있는 자동차보험이 없습니다."),
    CANNOT_REPORT_FIRE_ACCIDENT(HttpStatus.BAD_REQUEST, "고객님은 가입된 화재보험이 없어 화재사고를 접수하실 수 없습니다."),
    CANNOT_REPORT_INJURY_ACCIDENT(HttpStatus.BAD_REQUEST, "고객님은 가입된 건강보험이 없어 상해사고를 접수하실 수 없습니다."),
    ON_SITE_SYSTEM_RESPONSE_ERROR(HttpStatus.GATEWAY_TIMEOUT, "고장은 정상적으로 접수되었으나 현장출동 시스템 오류로 인해 현장출동 요청이 불가능합니다.\n고객센터(1588-1588)로 연락주시면 감사하겠습니다."),
    ACCIDENT_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "입력한 ID에 해당하는 사고 정보가 존재하지 않습니다."),
    MISMATCH_REQUEST_CLIENT_AND_ACCIDENT(HttpStatus.BAD_REQUEST, "요청하신 사고는 고객님이 접수한 사고가 아닙니다."),
    MISMATCH_ACCIDENT_TYPE(HttpStatus.BAD_REQUEST, "선택한 사고와 요청하신 사고의 유형이 일치하지 않습니다."),
    INSUFFICIENT_SUBMIT_ACC_DOC_FILE(HttpStatus.BAD_REQUEST, "요청에 필요한 파일이 불충분합니다."),
    ACCIDENT_DOCUMENT_TYPE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 사고 파일 타입으로 요청하였습니다."),
    CANNOT_CLAIM_CAR_BREAKDOWN(HttpStatus.BAD_REQUEST, "자동차 고장은 보상금을 청구할 수 없습니다."),
    CONTRACT_NOT_FOUND_REQUEST_CLIENT(HttpStatus.BAD_REQUEST, "요청하신 사고를 접수하기 위한 사고 유형과 관련된 고객님의 계약이 존재하지 않습니다."),
    NOT_EXIST_CLIENT_ACCIDENTS(HttpStatus.NOT_FOUND, "아직 고객님이 접수한 사고는 없습니다."),
    NOT_YET_ASSIGNED_COMP_EMPLOYEE(HttpStatus.BAD_REQUEST, "해당 사고는 아직 보상처리담당 직원이 배정되어 있지 않아 보상처리담당 직원을 변경하실 수 없습니다."),
    NOT_EXIST_COMP_EMPLOYEE_ACCIDENTS(HttpStatus.NOT_FOUND, "아직 할당된 사고가 없습니다."),
    MISMATCH_REQUEST_EMPLOYEE_AND_ACCIDENT(HttpStatus.BAD_REQUEST, "해당 사고는 직원분께 할당된 사고가 아닙니다."),
    CANNOT_INVESTIGATE_CAR_BREAKDOWN(HttpStatus.BAD_REQUEST, "자동차 고장은 손해조사할 수 없습니다."),
    CANNOT_PAY_COMPENSATION_CAR_BREAKDOWN(HttpStatus.BAD_REQUEST, "자동차 고장은 보상금을 지급할 수 없습니다."),
    NOT_EXIST_INVESTIGATE_ACCIDENT_FILE(HttpStatus.BAD_REQUEST, "사고조사보고서를 제출하지 않으셨습니다."),
    NOT_EXIST_LOSS_ASSESSMENT_FILE(HttpStatus.BAD_REQUEST, "손해사정서를 제출하지 않으셨습니다."),
    LOSS_ASSESSMENT_REJECTED(HttpStatus.BAD_REQUEST, "손해 사정서가 반려되었습니다. 사유 : [보상금 금액]")
    ;


    private final HttpStatus httpStatus;
    private final String errorMessage;

    public String getErrorName(){
        return this.name();
    }

}
