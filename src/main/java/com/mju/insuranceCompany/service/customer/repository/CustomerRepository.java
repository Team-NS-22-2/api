package com.mju.insuranceCompany.service.customer.repository;

import com.mju.insuranceCompany.service.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
