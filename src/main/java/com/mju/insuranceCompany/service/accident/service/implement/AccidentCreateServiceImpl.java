package com.mju.insuranceCompany.service.accident.service.implement;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.accident.controller.dto.*;
import com.mju.insuranceCompany.service.accident.domain.*;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccidentDocumentFile;
import com.mju.insuranceCompany.service.accident.exception.*;
import com.mju.insuranceCompany.service.accident.repository.AccidentRepository;
import com.mju.insuranceCompany.service.accident.service.interfaces.AccidentCreateService;
import com.mju.insuranceCompany.service.contract.domain.CarContract;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.outerSystem.RequestOnSiteSystem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccidentCreateServiceImpl implements AccidentCreateService {

    private final AccidentRepository accidentRepository;
    private final ContractRepository contractRepository;

    @Override
    public CarAccidentDto reportCarAccident(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        validateCustomerCanReportOnCar(accidentReportDto, customerId);

        Accident accident = Accident.createAccident(AccidentType.CAR_ACCIDENT, customerId, accidentReportDto);
        accidentRepository.save(accident);
        AccidentWorkerDto accidentWorkerDto = null;
        if(accident.isRequestOnSite()) {
            accidentWorkerDto = AccidentWorkerDto.toDto(RequestOnSiteSystem.connectWorker());
            if(accidentWorkerDto == null) { // TODO 시연을 위한 예외상황을 만들어줘야 하나?
                throw new OnSiteSystemResponseErrorException();
            }
        }
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return CarAccidentDto.toDto((CarAccident) accident, accidentWorkerDto, fileList);
    }

    @Override
    public CarBreakdownDto reportCarBreakdown(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        validateCustomerCanReportOnCar(accidentReportDto, customerId);

        Accident accident = Accident.createAccident(AccidentType.CAR_BREAKDOWN, customerId, accidentReportDto);
        accidentRepository.save(accident);
        AccidentWorkerDto accidentWorkerDto = AccidentWorkerDto.toDto(RequestOnSiteSystem.connectWorker());
        if(accidentWorkerDto == null) { // TODO 시연을 위한 예외상황을 만들어줘야 하나?
            throw new OnSiteSystemResponseErrorException();
        }
        return CarBreakdownDto.toDto((CarBreakdown) accident, accidentWorkerDto);
    }

    @Override
    public FireAccidentDto reportFireAccident(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        contractRepository.findFireContractByCustomerId(customerId)
                .filter(f -> {
                    if(f.isEmpty()) throw new CannotReportFireAccidentException();
                    return true;
                }).orElseThrow(ContractNotFoundRequestClientException::new);

        Accident accident = Accident.createAccident(AccidentType.FIRE_ACCIDENT, customerId, accidentReportDto);
        accidentRepository.save(accident);
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return FireAccidentDto.toDto((FireAccident) accident, fileList);
    }

    @Override
    public InjuryAccidentDto reportInjuryAccident(AccidentReportDto accidentReportDto) {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        contractRepository.findHealthContractByCustomerId(customerId)
                .filter(h -> {
                    if(h.isEmpty()) throw new CannotReportInjuryAccidentException();
                    return true;
                }).orElseThrow(ContractNotFoundRequestClientException::new);

        Accident accident = Accident.createAccident(AccidentType.INJURY_ACCIDENT, customerId, accidentReportDto);
        accidentRepository.save(accident);
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return InjuryAccidentDto.toDto((InjuryAccident) accident, fileList);
    }

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
                }).orElseThrow(ContractNotFoundRequestClientException::new);
        String requestedCarNo = accidentReportDto.getCarNo();
        contractList.stream()
                .filter(c -> c.getCarNo().equals(requestedCarNo))
                .findAny().orElseThrow(NotExistRequestedCarNoException::new); // 요청 고객이 가입한 자동차보험 중 사고 접수 요청한 자동차보험이 없는 경우 예외 발생
    }

}
