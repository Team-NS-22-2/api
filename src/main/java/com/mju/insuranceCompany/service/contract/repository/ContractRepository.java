package com.mju.insuranceCompany.service.contract.repository;

import com.mju.insuranceCompany.service.contract.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
}
