package com.mju.insuranceCompany.service.contract.applicationservice.interfaces;

import com.mju.insuranceCompany.service.contract.controller.dto.CustomerCarContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.CustomerFireContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.CustomerHealthContractDto;
import com.mju.insuranceCompany.service.contract.controller.dto.RegisterContractResponse;

public interface ContractCreateService {

    /** 건강보험 계약 */
    RegisterContractResponse registerHealthContract(int insuranceId, CustomerHealthContractDto dto);

    /** 자동차보험 계약 */
    RegisterContractResponse registerCarContract(int insuranceId, CustomerCarContractDto dto);

    /** 화재보험 계약 */
    RegisterContractResponse registerFireContract(int insuranceId, CustomerFireContractDto dto);

}
