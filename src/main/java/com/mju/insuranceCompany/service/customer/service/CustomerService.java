package com.mju.insuranceCompany.service.customer.service;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.customer.controller.dto.PaymentBasicInfoDto;
import com.mju.insuranceCompany.service.customer.controller.dto.PaymentCreateDto;
import com.mju.insuranceCompany.service.customer.domain.Customer;
import com.mju.insuranceCompany.service.customer.exception.CustomerNotFoundException;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service @Transactional @RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<PaymentBasicInfoDto> getAllPaymentInfos(){
        Customer customer = getCustomerByExtractedId();
        return customer.getPaymentList()
                .stream()
                .map(PaymentBasicInfoDto::toDto)
                .toList();
    }


    public void addNewPayment(PaymentCreateDto paymentCreateDto){
        Customer customer = getCustomerByExtractedId();
        customer.addPayment(paymentCreateDto);
    }

    private Customer getCustomerByExtractedId() {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        return customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }


}
