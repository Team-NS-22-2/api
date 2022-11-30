package com.mju.insuranceCompany.service.accident.service;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.accident.controller.dto.*;
import com.mju.insuranceCompany.service.accident.domain.*;
import com.mju.insuranceCompany.service.accident.exception.CannotReportCarAccidentException;
import com.mju.insuranceCompany.service.accident.exception.CannotReportFireAccidentException;
import com.mju.insuranceCompany.service.accident.exception.CannotReportInjuryAccidentException;
import com.mju.insuranceCompany.service.accident.exception.NotExistRequestedCarNoException;
import com.mju.insuranceCompany.service.accident.repository.AccidentRepository;
import com.mju.insuranceCompany.service.contract.domain.CarContract;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.outerSystem.RequestOnSiteSystem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccidentService {

    private final AccidentRepository accidentRepository;
    private final ContractRepository contractRepository;

    /**
     * 자동차 관련 사고 접수를 요청한 고객을 검증하는 메소드.
     * Throw 2 cases:
     * 1. 요청 고객이 자동차 보험에 가입되어 있지 않은 경우
     * 2. 요청 고객이 가입한 자동차보험 중 사고 접수 요청한 자동차보험이 없는 경우
     */
    private void validateCustomerCanReportOnCar(AccidentReportDto accidentReportDto, int customerId) {
        List<CarContract> contractList = contractRepository.findCarContractByCustomerId(customerId)
                .filter(c -> {
                    if (c.isEmpty()) throw new CannotReportCarAccidentException(); // 요청 고객이 자동차보험에 가입되어 있지 않은 경우 예외 발생
                    return true;
                }).orElseThrow();
        String requestedCarNo = accidentReportDto.getCarNo();
        contractList.stream()
                .filter(c -> c.getCarNo().equals(requestedCarNo))
                .findAny().orElseThrow(NotExistRequestedCarNoException::new); // 요청 고객이 가입한 자동차보험 중 사고 접수 요청한 자동차보험이 없는 경우 예외 발생
    }

    public CarAccidentDto reportCarAccident(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        validateCustomerCanReportOnCar(accidentReportDto, customerId);

        Accident accident = Accident.createAccident(AccidentType.CAR_ACCIDENT, customerId, accidentReportDto);
        accidentRepository.save(accident);
        AccidentWorkerDto accidentWorkerDto = null;
        if(accident.isRequestOnSite()) {
            accidentWorkerDto = AccidentWorkerDto.toDto(RequestOnSiteSystem.connectWorker());
        }
        return CarAccidentDto.toDto((CarAccident) accident, accidentWorkerDto);
    }

    public CarBreakdownDto reportCarBreakdown(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        validateCustomerCanReportOnCar(accidentReportDto, customerId);

        Accident accident = Accident.createAccident(AccidentType.CAR_BREAKDOWN, customerId, accidentReportDto);
        accidentRepository.save(accident);
        AccidentWorkerDto accidentWorkerDto = AccidentWorkerDto.toDto(RequestOnSiteSystem.connectWorker());
        return CarBreakdownDto.toDto((CarBreakdown) accident, accidentWorkerDto);
    }

    public FireAccidentDto reportFireAccident(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        contractRepository.findFireContractByCustomerId(customerId)
                .filter(f -> {
                    if(f.isEmpty()) throw new CannotReportFireAccidentException();
                    return true;
                }).orElseThrow();

        Accident accident = Accident.createAccident(AccidentType.FIRE_ACCIDENT, customerId, accidentReportDto);
        accidentRepository.save(accident);
        return FireAccidentDto.toDto((FireAccident) accident);
    }

    public InjuryAccidentDto reportInjuryAccident(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        contractRepository.findHealthContractByCustomerId(customerId)
                .filter(h -> {
                    if(h.isEmpty()) throw new CannotReportInjuryAccidentException();
                    return true;
                }).orElseThrow();

        Accident accident = Accident.createAccident(AccidentType.INJURY_ACCIDENT, customerId, accidentReportDto);
        accidentRepository.save(accident);
        return InjuryAccidentDto.toDto((InjuryAccident) accident);
    }
}
