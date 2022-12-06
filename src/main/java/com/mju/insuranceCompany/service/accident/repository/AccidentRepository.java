package com.mju.insuranceCompany.service.accident.repository;

import com.mju.insuranceCompany.service.accident.domain.Accident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository extends JpaRepository<Accident, Integer> {

    List<Accident> findAccidentByEmployeeId(int employeeId);

    List<Accident> findAllByCustomerId(int customerId);

    Optional<Accident> findCarAccidentById(int accidentId);


}
