package com.mju.insuranceCompany.application.dao.accident;


import com.mju.insuranceCompany.application.dao.CrudInterface;
import com.mju.insuranceCompany.application.domain.accident.Accident;

import java.util.List;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:38:56
 */
public interface AccidentDao extends CrudInterface<Accident> {

    List<Accident> readAllByCustomerId(int customerId);

    List<Accident> readAllByEmployeeId(int employeeid);
    void updateLossReserve(Accident accident);

    void updateLossReserveAndErrorRate(Accident accident);
    void updateCompEmployeeId(Accident accident);
    
}