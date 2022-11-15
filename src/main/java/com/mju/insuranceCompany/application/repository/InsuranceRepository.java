package com.mju.insuranceCompany.application.repository;

import com.mju.insuranceCompany.application.domain.insurance.HealthDetail;
import com.mju.insuranceCompany.application.domain.insurance.Insurance;
import com.mju.insuranceCompany.application.domain.insurance.InsuranceDetail;
import com.mju.insuranceCompany.application.domain.insurance.InsuranceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {

    @Query(
            "select h from HealthDetail h " +
                    "inner join Insurance i on i = h.insurance " +
                    "where i.id = :id"
    )
    List<InsuranceDetail> findHealthDetailsByInsuranceId(@Param("id") Integer id);

    @Query(
            "select f from FireDetail f " +
                    "inner join Insurance i on i = f.insurance " +
                    "where i.id = :id"
    )
    List<InsuranceDetail> findFireDetailsByInsuranceId(@Param("id") Integer id);

    @Query(
            "select c from CarDetail c " +
                    "inner join Insurance i on i = c.insurance " +
                    "where i.id = :id"
    )
    List<InsuranceDetail> findCarDetailsByInsuranceId(@Param("id") Integer id);

    @Query(
            "select i.insuranceType from Insurance i where i.id = :id"
    )
    Optional<InsuranceType> findInsuranceTypeByInsuranceId(@Param("id") Integer id);


}
