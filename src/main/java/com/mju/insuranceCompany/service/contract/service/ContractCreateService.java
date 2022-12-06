package com.mju.insuranceCompany.service.contract.service;


import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.contract.controller.dto.*;
import com.mju.insuranceCompany.service.contract.domain.CarContract;
import com.mju.insuranceCompany.service.contract.domain.Contract;
import com.mju.insuranceCompany.service.contract.domain.FireContract;
import com.mju.insuranceCompany.service.contract.domain.HealthContract;
import com.mju.insuranceCompany.service.contract.exception.MismatchInsuranceTypeAndRequestContractException;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.insuranceCompany.service.customer.domain.Customer;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import com.mju.insuranceCompany.service.employee.domain.Employee;
import com.mju.insuranceCompany.service.employee.repository.EmployeeRepository;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import com.mju.insuranceCompany.service.insurance.exception.InsuranceIdNotFoundException;
import com.mju.insuranceCompany.service.insurance.repository.InsuranceRepository;
import com.mju.insuranceCompany.service.user.domain.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.mju.insuranceCompany.service.user.domain.Users.anonymousUser;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ContractCreateService {

    private final InsuranceRepository insuranceRepository;
    private final CustomerRepository customerRepository;
    private final ContractRepository contractRepository;
    private final EmployeeRepository employeeRepository;


    public RegisterContractResponse registerHealthContract(int insId, CustomerHealthContractDto request) {
        request.setHealthContractDto(   // 판매직원이 보험을 체결할 경우, 직원ID를 주입하기 위함.
                (HealthContractDto) injectEmployeeIdToContractDto(request.getHealthContractDto()));

        InsuranceType insuranceType = insuranceRepository.findInsuranceTypeByInsuranceId(insId).orElseThrow(InsuranceIdNotFoundException::new);
        if(insuranceType != InsuranceType.HEALTH) {
            throw new MismatchInsuranceTypeAndRequestContractException();
        }

        Customer customer = new Customer(request.getCustomerDto());
        customerRepository.save(customer);

        request.getHealthContractDto().setInsuranceId(insId);
        Contract contract = new HealthContract(request.getHealthContractDto(), customer.getId());
        contractRepository.save(contract);

        return RegisterContractResponse.builder().customerId(customer.getId()).build();
    }

    public RegisterContractResponse registerCarContract(int insId, CustomerCarContractDto request) {
        request.setCarContractDto(   // 판매직원이 보험을 체결할 경우, 직원ID를 주입하기 위함.
                (CarContractDto) injectEmployeeIdToContractDto(request.getCarContractDto()));

        InsuranceType insuranceType = insuranceRepository.findInsuranceTypeByInsuranceId(insId).orElseThrow(InsuranceIdNotFoundException::new);
        if(insuranceType != InsuranceType.CAR) throw new MismatchInsuranceTypeAndRequestContractException();

        Customer customer = new Customer(request.getCustomerDto());
        customerRepository.save(customer);

        request.getCarContractDto().setInsuranceId(insId);

        Contract contract = new CarContract(request.getCarContractDto(), customer.getId());
        contractRepository.save(contract);

        return RegisterContractResponse.builder().customerId(customer.getId()).build();
    }

    public RegisterContractResponse registerFireContract(int insId, CustomerFireContractDto request) {
        request.setFireContractDto(   // 판매직원이 보험을 체결할 경우, 직원ID를 주입하기 위함.
                (FireContractDto) injectEmployeeIdToContractDto(request.getFireContractDto()));

        InsuranceType insuranceType = insuranceRepository.findInsuranceTypeByInsuranceId(insId).orElseThrow(InsuranceIdNotFoundException::new);
        if(insuranceType != InsuranceType.FIRE) throw new MismatchInsuranceTypeAndRequestContractException();

        Customer customer = new Customer(request.getCustomerDto());
        customerRepository.save(customer);

        request.getFireContractDto().setInsuranceId(insId);
        Contract contract = new FireContract(request.getFireContractDto(), customer.getId());
        contractRepository.save(contract);

        return RegisterContractResponse.builder().customerId(customer.getId()).build();
    }

    private Users getUsers(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")
                ? anonymousUser() : (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private ContractDto injectEmployeeIdToContractDto(ContractDto dto) {
        // 만일 지정된 직원 타입이 없다면(비회원 보험 가입 case), 기존 ContractDto를 리턴
        if (getUsers().getType() == null) return dto;
        else {
            int employeeId = AuthenticationExtractor.extractEmployeeIdByAuthentication();
            Employee employee = employeeRepository.findById(employeeId).orElseThrow();
            return dto.setEmployeeId(employee.getId());
        }
    }

}