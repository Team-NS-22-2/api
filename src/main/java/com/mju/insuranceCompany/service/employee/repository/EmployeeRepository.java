package com.mju.insuranceCompany.service.employee.repository;

import com.mju.insuranceCompany.service.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUserId(String userId);
}
