package com.mju.insuranceCompany.service.contract.applicationservice.interfaces;

import com.mju.insuranceCompany.service.contract.controller.dto.CustomerCarContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.CustomerFireContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.CustomerHealthContractDto;
import com.mju.insuranceCompany.service.customer.controller.dto.ContractReceiptDto;
import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;

import java.util.List;

public interface ContractReadService {

    List<ConditionOfUwOfCustomerResponse> getUwStateOfCustomer(InsuranceType insuranceType);

    CustomerHealthContractDto getHealthContractOfCustomerByContractId(int contractId);

    CustomerFireContractDto getFireContractOfCustomerByContractId(int contractId);

    CustomerCarContractDto getCarContractOfCustomerByContractId(int contractId);

    List<ContractReceiptDto> getAllContractReceipts();

}
