package com.mju.insuranceCompany.service.accident.repository;

import com.mju.insuranceCompany.service.accident.domain.Accident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccidentRepository extends JpaRepository<Accident, Integer> {

    List<Accident> findAccidentByEmployeeId(int employeeId);
}
