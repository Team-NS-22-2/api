package com.mju.insuranceCompany.service.accident.applicationservice.implement;

import com.mju.insuranceCompany.global.constant.StringConstant;
import com.mju.insuranceCompany.service.accident.controller.dto.*;
import com.mju.insuranceCompany.service.accident.domain.Accident;
import com.mju.insuranceCompany.service.accident.exception.AccidentIdNotFoundException;
import com.mju.insuranceCompany.service.accident.exception.InsufficientSubmitAccDocFileException;
import com.mju.insuranceCompany.service.accident.exception.NotYetAssignedCompEmployeeException;
import com.mju.insuranceCompany.service.accident.repository.AccidentRepository;
import com.mju.insuranceCompany.service.accident.applicationservice.interfaces.AccidentUpdateService;
import com.mju.insuranceCompany.service.accident.applicationservice.interfaces.CompEmployeeAssignService;
import com.mju.insuranceCompany.service.customer.domain.Customer;
import com.mju.insuranceCompany.service.customer.exception.CustomerNotFoundException;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import com.mju.insuranceCompany.service.employee.domain.Employee;
import com.mju.outerSystem.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccidentUpdateServiceImpl implements AccidentUpdateService {

    private final AccidentRepository accidentRepository;
    private final CustomerRepository customerRepository;
    private final CompEmployeeAssignService compEmployeeAssignService;

    @Override
    public CompEmployeeDto claimCompensation(int accidentId) {
        Accident accident = this.getAccidentById(accidentId);
        if(!accident.checkConditionForClaimCompensation(accident.getAccidentType())) {
            throw new InsufficientSubmitAccDocFileException(); // 보상금 청구에 만족하는 파일이 모두 저장되어 있지 않는 경우
        }

        Employee employee = compEmployeeAssignService.assignCompEmployee();
        accident.assignEmployeeId(employee.getId());
        return CompEmployeeDto.toDto(employee);
    }

    @Override
    public CompEmployeeDto changeCompEmployee(int accidentId, ComplainRequestDto dto) {
        Accident accident = this.getAccidentById(accidentId);
        accident.validateClient();
        if(accident.getEmployeeId() == 0) { // validate of 보험직원을 변경할 사고에 보상담당직원이 배정되어 있지 않은 경우
            throw new NotYetAssignedCompEmployeeException();
        }
        // 1. Complain 등록
        Customer customer = customerRepository.findById(accident.getCustomerId())
                .orElseThrow(CustomerNotFoundException::new);
        customer.addComplain(dto);

        // 2. 보상담당자 배정
        Employee employee = compEmployeeAssignService.changeCompEmployee(accident.getEmployeeId());
        accident.assignEmployeeId(employee.getId());
        return CompEmployeeDto.toDto(employee);
    }

    @Override
    public void investigateAccident(int accidentId, InvestigateAccidentDto dto) {
        Accident accident = this.getAccidentById(accidentId);
        accident.validateCompEmployee();
        accident.investigate(dto);
    }

    @Override
    public PaymentOfCompensationResultDto payCompensation(int accidentId, PaymentOfCompensationDto dto) {
        Accident accident = this.getAccidentById(accidentId);
        accident.validateCompEmployee();
        dto = accident.payCompensation(dto);
        String message;
        if(dto.isPay()) {
            message = Bank.pay(dto.getBank(), dto.getAccountNo(), dto.getAmount());
        } else {
            message = StringConstant.MESSAGE_DO_NOT_NEED_PAY_COMPENSATION;
        }
        return new PaymentOfCompensationResultDto(message);
    }

    /** Read accident from repository */
    private Accident getAccidentById(int accidentId) {
        return accidentRepository.findById(accidentId).orElseThrow(AccidentIdNotFoundException::new);
    }
}
