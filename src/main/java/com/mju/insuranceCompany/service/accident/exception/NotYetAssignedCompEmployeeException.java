package com.mju.insuranceCompany.service.accident.exception;

import com.mju.insuranceCompany.global.exception.ErrorCode;
import com.mju.insuranceCompany.global.exception.MyException;

public class NotYetAssignedCompEmployeeException extends MyException {
    public NotYetAssignedCompEmployeeException() {
        super(AccidentErrorCode.NOT_YET_ASSIGNED_COMP_EMPLOYEE);
    }
}
