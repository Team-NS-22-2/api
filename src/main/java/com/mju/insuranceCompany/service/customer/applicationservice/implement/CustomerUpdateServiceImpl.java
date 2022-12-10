package com.mju.insuranceCompany.service.customer.applicationservice.implement;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.customer.controller.dto.PaymentCreateDto;
import com.mju.insuranceCompany.service.customer.domain.Customer;
import com.mju.insuranceCompany.service.customer.exception.CustomerNotFoundException;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import com.mju.insuranceCompany.service.customer.applicationservice.interfaces.CustomerUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerUpdateServiceImpl implements CustomerUpdateService {

    private final CustomerRepository customerRepository;

    @Override
    public void addNewPayment(PaymentCreateDto paymentCreateDto) {
        Customer customer = getCustomerByExtractedId();
        customer.addPayment(paymentCreateDto);
    }

    private Customer getCustomerByExtractedId() {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        return customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }
}
