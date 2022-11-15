package com.mju.insuranceCompany.application.repository;

import com.mju.insuranceCompany.application.domain.contract.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
}
