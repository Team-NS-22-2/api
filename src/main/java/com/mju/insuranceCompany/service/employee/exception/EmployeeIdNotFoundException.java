package com.mju.insuranceCompany.service.employee.exception;

import com.mju.insuranceCompany.global.exception.MyException;

public class EmployeeIdNotFoundException extends MyException {

    public EmployeeIdNotFoundException() {
        super(EmployeeErrorCode.EMPLOYEE_ID_NOT_FOUND);
    }
}
