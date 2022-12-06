package com.mju.insuranceCompany.service.insurance.repository;

import com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceOfDeveloperDto;
import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {

    @Query(
            "select i.insuranceType from Insurance i where i.id = :id"
    )
    Optional<InsuranceType> findInsuranceTypeByInsuranceId(@Param("id") Integer id);

    @Query(
            "select new com.mju.insuranceCompany.service.insurance.controller.dto.InsuranceOfDeveloperDto(" +
                    "i.id, i.insuranceType, i.name, i.description, d.developDate, d.salesAuthorizationState, d.salesStartDate) " +
                    "from Insurance as i inner join " + "DevelopInfo as d " +
                    "on i.id = d.insuranceId " +
                    "and d.insuranceId in (select d.insuranceId from DevelopInfo as d where d.employeeId = :employeeId)"
    )
    List<InsuranceOfDeveloperDto> findInsuranceOfDeveloperByEmployeeId(@Param("employeeId") Integer employeeId);

}
