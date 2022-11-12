package com.mju.insuranceCompany.application.dao.employee;


import com.mju.insuranceCompany.application.domain.employee.Employee;
import com.mju.insuranceCompany.application.dao.CrudInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ����
 * @version 1.0
 * @created 09-5-2022 ���� 4:38:59
 */
public interface EmployeeDao extends CrudInterface<Employee> {

    public List<Employee> readAllCompEmployee();

    public ArrayList<Employee> readAll();
}