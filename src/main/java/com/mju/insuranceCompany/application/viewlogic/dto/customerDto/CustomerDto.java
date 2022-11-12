package com.mju.insuranceCompany.application.viewlogic.dto.customerDto;

import com.mju.insuranceCompany.application.domain.customer.Customer;

public class CustomerDto {

    private String name;
    private String ssn;
    private String address;
    private String phone;
    private String email;
    private String job;

    public CustomerDto(String name, String ssn, String address, String phone, String email, String job) {
        this.name = name;
        this.ssn = ssn;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.job = job;
    }

    public CustomerDto(Customer customer) {
        this.name = customer.getName();
        this.ssn = customer.getSsn();
        this.address = customer.getAddress();
        this.phone = customer.getPhone();
        this.email = customer.getEmail();
        this.job = customer.getJob();
    }

    public String getName() {
        return name;
    }

    public String getSsn() {
        return ssn;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getJob() {
        return job;
    }
}
