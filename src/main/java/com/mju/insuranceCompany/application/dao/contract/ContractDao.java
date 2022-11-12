package com.mju.insuranceCompany.application.dao.contract;


import com.mju.insuranceCompany.application.dao.CrudInterface;
import com.mju.insuranceCompany.application.domain.contract.Contract;
import com.mju.insuranceCompany.application.viewlogic.dto.contractDto.ContractwithTypeDto;

import java.util.List;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:38:58
 */
public interface ContractDao extends CrudInterface<Contract> {

    List<ContractwithTypeDto> findAllContractWithTypeByCustomerId(int customerId);
    List<Contract> findAllByCustomerId(int customerId);
    void updatePayment(int contractId, int paymentId);
}