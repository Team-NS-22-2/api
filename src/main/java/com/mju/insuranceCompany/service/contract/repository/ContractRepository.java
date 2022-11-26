package com.mju.insuranceCompany.service.contract.repository;

import com.mju.insuranceCompany.service.contract.domain.CarContract;
import com.mju.insuranceCompany.service.contract.domain.Contract;
import com.mju.insuranceCompany.service.contract.domain.FireContract;
import com.mju.insuranceCompany.service.contract.domain.HealthContract;
import com.mju.insuranceCompany.service.customer.controller.dto.ContractReceiptDto;
import com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse;
import com.mju.insuranceCompany.service.insurance.domain.InsuranceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    @Query(
            "select new com.mju.insuranceCompany.service.employee.controller.dto.ConditionOfUwOfCustomerResponse(ct.id, cs.name, ct.conditionOfUw) " +
                    "from Customer cs, Contract ct where cs.id = ct.customerId " +
                    "and (ct.conditionOfUw = 'WAIT' or ct.conditionOfUw = 'RE_AUDIT') " +
                    "and ct.insuranceId = (select i.id from Insurance i where i.insuranceType = :insuranceType)"
    )
    List<ConditionOfUwOfCustomerResponse> findConditionOfUwOfCustomer(InsuranceType insuranceType);

    Optional<HealthContract> findHealthContractById(int contractId);

    Optional<FireContract> findFireContractById(int contractId);

    Optional<CarContract> findCarContractById(int contractId);

    @Query("select new com.mju.insuranceCompany.service.customer.controller.dto.ContractReceiptDto(c.id, i.name, c.premium ) " +
            "from Contract c inner join Insurance i on c.insuranceId = i.id " +
            "where c.customerId = :customerId")
    List<ContractReceiptDto> findAllContractReceipt(@Param("customerId") int customerId);


}
