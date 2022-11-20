package com.mju.insuranceCompany.service.employee.repository;

import com.mju.insuranceCompany.service.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
