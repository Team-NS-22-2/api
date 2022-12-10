package com.mju.insuranceCompany.service.customer.service.implement;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.customer.controller.dto.PaymentBasicInfoDto;
import com.mju.insuranceCompany.service.customer.domain.Customer;
import com.mju.insuranceCompany.service.customer.exception.CustomerNotFoundException;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import com.mju.insuranceCompany.service.customer.service.interfaces.CustomerReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerReadServiceImpl implements CustomerReadService {

    private final CustomerRepository customerRepository;

    @Override
    public List<PaymentBasicInfoDto> getAllPaymentInfos() {
        Customer customer = getCustomerByExtractedId();
        return customer.readPayments()
                .stream()
                .map(PaymentBasicInfoDto::toDto)
                .toList();
    }

    private Customer getCustomerByExtractedId() {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        return customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }
}

