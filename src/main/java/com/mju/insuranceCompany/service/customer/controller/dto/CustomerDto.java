package com.mju.insuranceCompany.service.customer.controller.dto;

import com.mju.insuranceCompany.service.customer.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerDto {
    private String name;
    private String ssn;
    private String phone;
    private String address;
    private String email;
    private String job;

    public static CustomerDto toDtoFromEntity(Customer customer) {
        return new CustomerDto(
                customer.getName(),
                customer.getSsn(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getJob()
        );
    }
}
