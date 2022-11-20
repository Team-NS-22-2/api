package com.mju.insuranceCompany.service.customer.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerBasicDto {
    private String name;
    private String ssn;
    private String phone;
    private String address;
    private String email;
    private String job;
}
