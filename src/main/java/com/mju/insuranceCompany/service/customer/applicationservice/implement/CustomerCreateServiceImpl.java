package com.mju.insuranceCompany.service.customer.applicationservice.implement;

import com.mju.insuranceCompany.service.customer.controller.dto.CustomerDto;
import com.mju.insuranceCompany.service.customer.domain.Customer;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import com.mju.insuranceCompany.service.customer.applicationservice.interfaces.CustomerCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerCreateServiceImpl implements CustomerCreateService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(CustomerDto dto) {
        Customer customer = new Customer(dto);
        return customerRepository.save(customer);
    }

}
