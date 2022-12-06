package com.mju.insuranceCompany.service.accident.service.interfaces;

import com.mju.insuranceCompany.service.accident.controller.dto.*;

public interface AccidentUpdateService {

    /** 보상금 청구 */
    CompEmployeeDto claimCompensation(int accidentId);

    /** 보상처리담당 직원 변경 */
    CompEmployeeDto changeCompEmployee(int accidentId, ComplainRequestDto dto);

    /** 손해조사 */
    void investigateAccident(int accidentId, InvestigateAccidentDto dto);

    /** 보상금 지급 */
    PaymentOfCompensationResultDto payCompensation(int accidentId, PaymentOfCompensationDto dto);

}
