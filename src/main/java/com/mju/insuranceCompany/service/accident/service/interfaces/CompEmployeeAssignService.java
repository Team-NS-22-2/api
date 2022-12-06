package com.mju.insuranceCompany.service.accident.service.interfaces;

import com.mju.insuranceCompany.service.employee.domain.Employee;

public interface CompEmployeeAssignService {

    Employee assignCompEmployee();

    Employee changeCompEmployee(int currentEmployeeId);

}
