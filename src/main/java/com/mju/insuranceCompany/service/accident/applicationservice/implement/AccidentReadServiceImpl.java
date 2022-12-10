package com.mju.insuranceCompany.service.accident.applicationservice.implement;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.accident.controller.dto.*;
import com.mju.insuranceCompany.service.accident.domain.*;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccidentDocumentFile;
import com.mju.insuranceCompany.service.accident.exception.AccidentIdNotFoundException;
import com.mju.insuranceCompany.service.accident.exception.NotExistClientAccidentsException;
import com.mju.insuranceCompany.service.accident.exception.NotExistCompEmployeeAccidentsException;
import com.mju.insuranceCompany.service.accident.repository.AccidentRepository;
import com.mju.insuranceCompany.service.accident.applicationservice.interfaces.AccidentReadService;
import com.mju.insuranceCompany.service.customer.controller.dto.CustomerDto;
import com.mju.insuranceCompany.service.customer.exception.CustomerNotFoundException;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import com.mju.insuranceCompany.service.employee.domain.Employee;
import com.mju.insuranceCompany.service.employee.exception.EmployeeIdNotFoundException;
import com.mju.insuranceCompany.service.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccidentReadServiceImpl implements AccidentReadService {

    private final AccidentRepository accidentRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<AccidentListInfoDto> getAccidentListOfCustomer() {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        List<Accident> accidentList = accidentRepository.findAllByCustomerId(customerId);
        if(accidentList.isEmpty()) throw new NotExistClientAccidentsException(); // 고객이 접수한 사고가 없을 경우

        List<AccidentListInfoDto> accidentListInfoDtoList = new ArrayList<>();
        for(Accident accident : accidentList) {
            CompEmployeeDto compEmployee = null;
            int employeeId = accident.getEmployeeId();
            if(employeeId != 0) {
                Employee employee = employeeRepository.findById(employeeId)
                        .orElseThrow(EmployeeIdNotFoundException::new);
                compEmployee = CompEmployeeDto.toDto(employee);
            }
            accidentListInfoDtoList.add(AccidentListInfoDto.toDto(accident, compEmployee));
        }
        return accidentListInfoDtoList;
    }

    @Override
    public CarAccidentDto getCarAccidentOfCustomer(int accidentId) {
        Accident accident = accidentRepository.findById(accidentId).orElseThrow(AccidentIdNotFoundException::new);
        accident.validateClient();
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return CarAccidentDto.toDto((CarAccident) accident, null, fileList);
    }

    @Override
    public CarBreakdownDto getCarBreakdownOfCustomer(int accidentId) {
        Accident accident = accidentRepository.findById(accidentId).orElseThrow(AccidentIdNotFoundException::new);
        accident.validateClient();
        return CarBreakdownDto.toDto((CarBreakdown) accident, null);
    }

    @Override
    public FireAccidentDto getFireAccidentOfCustomer(int accidentId) {
        Accident accident = accidentRepository.findById(accidentId).orElseThrow(AccidentIdNotFoundException::new);
        accident.validateClient();
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return FireAccidentDto.toDto((FireAccident) accident, fileList);
    }

    @Override
    public InjuryAccidentDto getInjuryAccidentOfCustomer(int accidentId) {
        Accident accident = accidentRepository.findById(accidentId).orElseThrow(AccidentIdNotFoundException::new);
        accident.validateClient();
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return InjuryAccidentDto.toDto((InjuryAccident) accident, fileList);
    }

    @Override
    public List<AccidentListInfoDto> getAccidentListOfCompEmployee() {
        int compEmployeeId = AuthenticationExtractor.extractEmployeeIdByAuthentication();
        List<AccidentListInfoDto> accidentList =
                accidentRepository.findAccidentByEmployeeId(compEmployeeId).stream()
                        .map(AccidentListInfoDto::toDto).toList();
        if(accidentList.isEmpty()) {
            throw new NotExistCompEmployeeAccidentsException();
        }
        return accidentList;
    }

    @Override
    public List<AccidentListInfoDto> getAccidentListOfCompEmployeeByCompState(CompState compState) {
        int compEmployeeId = AuthenticationExtractor.extractEmployeeIdByAuthentication();
        List<AccidentListInfoDto> accidentList =
                accidentRepository.findAccidentByEmployeeId(compEmployeeId).stream()
                        .filter(a -> a.getCompState().equals(compState))
                        .map(AccidentListInfoDto::toDto).toList();

        if(accidentList.isEmpty()) {
            throw new NotExistCompEmployeeAccidentsException();
        }
        return accidentList;
    }

    @Override
    public CompCarAccidentDto getCarAccidentOfCompEmployee(int accidentId) {
        Accident accident = this.getAccidentById(accidentId);
        accident.validateCompEmployee();
        CustomerDto customerDto = customerRepository.findById(accident.getCustomerId())
                .map(CustomerDto::toDto)
                .orElseThrow(CustomerNotFoundException::new);
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return CompCarAccidentDto.toDto((CarAccident) accident, customerDto, fileList);
    }

    @Override
    public CompFireAccidentDto getFireAccidentOfCompEmployee(int accidentId) {
        Accident accident = this.getAccidentById(accidentId);
        accident.validateCompEmployee();
        CustomerDto customerDto = customerRepository.findById(accident.getCustomerId())
                .map(CustomerDto::toDto)
                .orElseThrow(CustomerNotFoundException::new);
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return CompFireAccidentDto.toDto((FireAccident) accident, customerDto, fileList);
    }

    @Override
    public CompInjuryAccidentDto getInjuryAccidentOfCompEmployee(int accidentId) {
        Accident accident = this.getAccidentById(accidentId);
        accident.validateCompEmployee();
        CustomerDto customerDto = customerRepository.findById(accident.getCustomerId())
                .map(CustomerDto::toDto)
                .orElseThrow(CustomerNotFoundException::new);
        List<AccidentDocumentFile> fileList = accident.getAccidentDocumentFileList();
        return CompInjuryAccidentDto.toDto((InjuryAccident) accident, customerDto, fileList);
    }

    /** Read accident from repository */
    private Accident getAccidentById(int accidentId) {
        return accidentRepository.findById(accidentId).orElseThrow(AccidentIdNotFoundException::new);
    }
}
