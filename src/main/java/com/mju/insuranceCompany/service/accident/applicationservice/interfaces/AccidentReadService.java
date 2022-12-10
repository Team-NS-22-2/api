package com.mju.insuranceCompany.service.accident.applicationservice.interfaces;

import com.mju.insuranceCompany.service.accident.controller.dto.*;
import com.mju.insuranceCompany.service.accident.domain.CompState;

import java.util.List;

public interface AccidentReadService {

    /** 로그인 고객의 사고 접수 리스트 조회 */
    List<AccidentListInfoDto> getAccidentListOfCustomer();

    /** 고객 자동차 사고 정보 조회 */
    CarAccidentDto getCarAccidentOfCustomer(int accidentId);

    /** 고객 자동차 고장 정보 조회 */
    CarBreakdownDto getCarBreakdownOfCustomer(int accidentId);

    /** 고객 화재 사고 정보 조회 */
    FireAccidentDto getFireAccidentOfCustomer(int accidentId);

    /** 고객 상해 사고 정보 조회 */
    InjuryAccidentDto getInjuryAccidentOfCustomer(int accidentId);

    /** 보상직원에게 할당된 사고 리스트 조회 */
    List<AccidentListInfoDto> getAccidentListOfCompEmployee();

    List<AccidentListInfoDto> getAccidentListOfCompEmployeeByCompState(CompState compState);

    /** 보상직원 자동차 사고 정보 조회 */
    CompCarAccidentDto getCarAccidentOfCompEmployee(int accidentId);

    /** 보상직원 화재 사고 정보 조회 */
    CompFireAccidentDto getFireAccidentOfCompEmployee(int accidentId);

    /** 보상직원 상해 사고 정보 조회 */
    CompInjuryAccidentDto getInjuryAccidentOfCompEmployee(int accidentId);

}
