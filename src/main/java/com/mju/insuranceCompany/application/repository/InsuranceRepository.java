package com.mju.insuranceCompany.application.repository;

import com.mju.insuranceCompany.application.domain.insurance.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {

}
