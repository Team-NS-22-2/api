package com.mju.insuranceCompany.application.repository;

import com.mju.insuranceCompany.application.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
