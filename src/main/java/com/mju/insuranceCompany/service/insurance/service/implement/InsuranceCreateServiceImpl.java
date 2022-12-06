package com.mju.insuranceCompany.service.insurance.service.implement;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.employee.domain.Employee;
import com.mju.insuranceCompany.service.employee.exception.EmployeeIdNotFoundException;
import com.mju.insuranceCompany.service.employee.repository.EmployeeRepository;
import com.mju.insuranceCompany.service.insurance.controller.dto.SaveCarInsuranceDto;
import com.mju.insuranceCompany.service.insurance.controller.dto.SaveFireInsuranceDto;
import com.mju.insuranceCompany.service.insurance.controller.dto.SaveHealthInsuranceDto;
import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import com.mju.insuranceCompany.service.insurance.repository.InsuranceRepository;
import com.mju.insuranceCompany.service.insurance.service.interfaces.InsuranceCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InsuranceCreateServiceImpl implements InsuranceCreateService {

    private final InsuranceRepository insuranceRepository;
    private final EmployeeRepository employeeRepository;

    public void saveHealthInsurance(SaveHealthInsuranceDto saveHealthInsuranceDto) {
        int employeeId = AuthenticationExtractor.extractEmployeeIdByAuthentication();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeIdNotFoundException::new);
        Insurance healthInsurance = Insurance.createHealthInsurance(
                saveHealthInsuranceDto.getInsuranceBasicInfoDto(),
                saveHealthInsuranceDto.getGuaranteeDtoList(),
                employee.getId(),
                saveHealthInsuranceDto.getHealthDetailDtoList()
        );
        insuranceRepository.save(healthInsurance);
    }

    public void saveCarInsurance(SaveCarInsuranceDto saveCarInsuranceDto) {
        int employeeId = AuthenticationExtractor.extractEmployeeIdByAuthentication();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeIdNotFoundException::new);
        Insurance carInsurance = Insurance.createCarInsurance(
                saveCarInsuranceDto.getInsuranceBasicInfoDto(),
                saveCarInsuranceDto.getGuaranteeDtoList(),
                employee.getId(),
                saveCarInsuranceDto.getCarDetailDtoList()
        );
        insuranceRepository.save(carInsurance);
    }

    public void saveFireInsurance(SaveFireInsuranceDto saveFireInsuranceDto) {
        int employeeId = AuthenticationExtractor.extractEmployeeIdByAuthentication();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeIdNotFoundException::new);
        Insurance fireInsurance = Insurance.createFireInsurance(
                saveFireInsuranceDto.getInsuranceBasicInfoDto(),
                saveFireInsuranceDto.getGuaranteeDtoList(),
                employee.getId(),
                saveFireInsuranceDto.getFireDetailDtoList()
        );
        insuranceRepository.save(fireInsurance);
    }

}
