package com.mju.insuranceCompany.service.customer.applicationservice.interfaces;

import com.mju.insuranceCompany.service.customer.controller.dto.CustomerDto;
import com.mju.insuranceCompany.service.customer.domain.Customer;

public interface CustomerCreateService {

    Customer createCustomer(CustomerDto dto);

}
