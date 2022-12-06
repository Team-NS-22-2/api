package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import com.mju.insuranceCompany.global.exception.MyException;

public class NotExistCompEmployeeAccidentsException extends MyException {
    public NotExistCompEmployeeAccidentsException() {
        super(AccidentErrorCode.NOT_EXIST_COMP_EMPLOYEE_ACCIDENTS);
    }
}
