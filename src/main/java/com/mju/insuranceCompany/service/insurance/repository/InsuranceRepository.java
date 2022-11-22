package com.mju.insuranceCompany.service.insurance.repository;

import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {

    @Query(
            "select i.insuranceType from Insurance i where i.id = :id"
    )
    Optional<InsuranceType> findInsuranceTypeByInsuranceId(@Param("id") Integer id);

}
