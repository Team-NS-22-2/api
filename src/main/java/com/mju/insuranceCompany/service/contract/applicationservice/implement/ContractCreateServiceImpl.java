package com.mju.insuranceCompany.service.contract.applicationservice.implement;


import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.contract.controller.dto.*;
import com.mju.insuranceCompany.service.contract.domain.CarContract;
import com.mju.insuranceCompany.service.contract.domain.Contract;
import com.mju.insuranceCompany.service.contract.domain.FireContract;
import com.mju.insuranceCompany.service.contract.domain.HealthContract;
import com.mju.insuranceCompany.service.contract.exception.MismatchInsuranceTypeAndRequestContractException;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.insuranceCompany.service.contract.applicationservice.interfaces.ContractCreateService;
import com.mju.insuranceCompany.service.customer.domain.Customer;
import com.mju.insuranceCompany.service.customer.applicationservice.interfaces.CustomerCreateService;
import com.mju.insuranceCompany.service.employee.domain.Employee;
import com.mju.insuranceCompany.service.employee.repository.EmployeeRepository;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import com.mju.insuranceCompany.service.insurance.exception.InsuranceIdNotFoundException;
import com.mju.insuranceCompany.service.insurance.repository.InsuranceRepository;
import com.mju.insuranceCompany.service.auth.domain.Auth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.mju.insuranceCompany.service.auth.domain.Auth.anonymousUser;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ContractCreateServiceImpl implements ContractCreateService {

    private final ContractRepository contractRepository;
    private final InsuranceRepository insuranceRepository;
    private final EmployeeRepository employeeRepository;

    private final CustomerCreateService customerCreateService;

    @Override
    public RegisterContractResponse registerHealthContract(int insuranceId, CustomerHealthContractDto dto) {
        InsuranceType insuranceType = insuranceRepository.findInsuranceTypeByInsuranceId(insuranceId).orElseThrow(InsuranceIdNotFoundException::new);
        if(insuranceType != InsuranceType.HEALTH) {
            throw new MismatchInsuranceTypeAndRequestContractException();
        }

        dto.setHealthContractDto(   // ??????????????? ????????? ????????? ??????, ??????ID??? ???????????? ??????.
                (HealthContractDto) injectEmployeeIdToContractDto(dto.getHealthContractDto()));

        Customer customer = customerCreateService.createCustomer(dto.getCustomerDto());

        dto.getHealthContractDto().setInsuranceId(insuranceId);
        Contract contract = new HealthContract(dto.getHealthContractDto(), customer.getId());
        contractRepository.save(contract);

        return RegisterContractResponse.builder().customerId(customer.getId()).build();
    }

    @Override
    public RegisterContractResponse registerCarContract(int insuranceId, CustomerCarContractDto dto) {
        InsuranceType insuranceType = insuranceRepository.findInsuranceTypeByInsuranceId(insuranceId).orElseThrow(InsuranceIdNotFoundException::new);
        if(insuranceType != InsuranceType.CAR) throw new MismatchInsuranceTypeAndRequestContractException();

        dto.setCarContractDto(   // ??????????????? ????????? ????????? ??????, ??????ID??? ???????????? ??????.
                (CarContractDto) injectEmployeeIdToContractDto(dto.getCarContractDto()));

        Customer customer = customerCreateService.createCustomer(dto.getCustomerDto());

        dto.getCarContractDto().setInsuranceId(insuranceId);
        Contract contract = new CarContract(dto.getCarContractDto(), customer.getId());
        contractRepository.save(contract);

        return RegisterContractResponse.builder().customerId(customer.getId()).build();
    }

    @Override
    public RegisterContractResponse registerFireContract(int insuranceId, CustomerFireContractDto dto) {
        InsuranceType insuranceType = insuranceRepository.findInsuranceTypeByInsuranceId(insuranceId).orElseThrow(InsuranceIdNotFoundException::new);
        if(insuranceType != InsuranceType.FIRE) throw new MismatchInsuranceTypeAndRequestContractException();

        dto.setFireContractDto(   // ??????????????? ????????? ????????? ??????, ??????ID??? ???????????? ??????.
                (FireContractDto) injectEmployeeIdToContractDto(dto.getFireContractDto()));

        Customer customer = customerCreateService.createCustomer(dto.getCustomerDto());

        dto.getFireContractDto().setInsuranceId(insuranceId);
        Contract contract = new FireContract(dto.getFireContractDto(), customer.getId());
        contractRepository.save(contract);

        return RegisterContractResponse.builder().customerId(customer.getId()).build();
    }

    private Auth getAuth(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")
                ? anonymousUser() : (Auth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private ContractDto injectEmployeeIdToContractDto(ContractDto dto) {
        // ?????? ????????? ?????? ????????? ?????????(????????? ?????? ?????? case), ?????? ContractDto??? ??????
        if (getAuth().getType() == null) return dto;
        else {
            int employeeId = AuthenticationExtractor.extractEmployeeIdByAuthentication();
            Employee employee = employeeRepository.findById(employeeId).orElseThrow();
            return dto.setEmployeeId(employee.getId());
        }
    }

}