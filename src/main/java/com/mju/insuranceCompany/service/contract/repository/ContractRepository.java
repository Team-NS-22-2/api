package com.mju.insuranceCompany.service.contract.repository;

import com.mju.insuranceCompany.service.contract.domain.Contract;
import com.mju.insuranceCompany.service.contract.domain.HealthContract;
import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    @Query(
            "select new com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse(cs.id, cs.name, ct.conditionOfUw) " +
                    "from Customer cs, Contract ct where cs.id = ct.customerId and ct.insuranceId = (" +
                    "select i.id from Insurance i where i.insuranceType = :insuranceType)"
    )
    List<ConditionOfUwOfCustomerResponse> findConditionOfUwOfCustomer(InsuranceType insuranceType);

    Optional<HealthContract> findHealthContractByCustomerId(int customerId);
}
