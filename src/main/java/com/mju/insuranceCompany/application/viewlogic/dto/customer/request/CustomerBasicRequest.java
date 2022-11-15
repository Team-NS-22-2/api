package com.mju.insuranceCompany.application.viewlogic.dto.customer.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerBasicRequest {
    private String name;
    private String ssn;
    private String phone;
    private String address;
    private String email;
    private String job;
}
