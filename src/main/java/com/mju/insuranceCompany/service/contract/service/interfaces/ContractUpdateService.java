package com.mju.insuranceCompany.service.contract.service.interfaces;

import com.mju.insuranceCompany.service.contract.domain.ConditionOfUw;

public interface ContractUpdateService {

    void underwriting(int contractId, String reasonOfUw, ConditionOfUw conditionOfUw);

}
