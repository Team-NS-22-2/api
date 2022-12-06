package com.mju.insuranceCompany.service.employee.repository;

import com.mju.insuranceCompany.service.employee.domain.Department;
import com.mju.insuranceCompany.service.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findEmployeeByDepartmentEquals(Department dept);

}
