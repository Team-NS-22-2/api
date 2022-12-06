package com.mju.insuranceCompany.service.contract.service.implement;


import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.service.contract.controller.dto.PaymentRegisterOnContractDto;
import com.mju.insuranceCompany.service.contract.domain.Contract;
import com.mju.insuranceCompany.service.contract.repository.ContractRepository;
import com.mju.insuranceCompany.service.contract.service.interfaces.PaymentRegisterService;
import com.mju.insuranceCompany.service.customer.domain.Customer;
import com.mju.insuranceCompany.service.customer.domain.payment.Payment;
import com.mju.insuranceCompany.service.customer.exception.CustomerNotFoundException;
import com.mju.insuranceCompany.service.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor @Transactional
public class PaymentRegisterServiceImpl implements PaymentRegisterService {

    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void registerPaymentOnContract(PaymentRegisterOnContractDto dto){
        Customer customer = getCustomerByExtractedId();
        Contract contract = contractRepository.findById(dto.getContractId()).orElseThrow();


        Payment payment = customer.getPayment(dto.getPaymentId());
        contract.registerPayment(payment);
    }

    private Customer getCustomerByExtractedId() {
        int customerId = AuthenticationExtractor.extractCustomerIdByAuthentication();
        return customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }
}
