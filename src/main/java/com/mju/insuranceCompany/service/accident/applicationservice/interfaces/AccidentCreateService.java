package com.mju.insuranceCompany.service.accident.applicationservice.interfaces;

import com.mju.insuranceCompany.service.accident.controller.dto.*;

public interface AccidentCreateService {

    /** 자동차 사고 접수 */
    CarAccidentDto reportCarAccident(AccidentReportDto accidentReportDto);

    /** 자동차 고장 접수 */
    CarBreakdownDto reportCarBreakdown(AccidentReportDto accidentReportDto);

    /** 화재 사고 접수 */
    FireAccidentDto reportFireAccident(AccidentReportDto accidentReportDto);

    /** 상해 사고 접수 */
    InjuryAccidentDto reportInjuryAccident(AccidentReportDto accidentReportDto);

}
