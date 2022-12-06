package com.mju.insuranceCompany.service.accident.controller.dto;

import com.mju.insuranceCompany.service.employee.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompEmployeeDto {
    private String name;
    private String phone;

    public static CompEmployeeDto toDto(Employee employee) {
        return new CompEmployeeDto(
                employee.getName(),
                employee.getPhone()
        );
    }
}
