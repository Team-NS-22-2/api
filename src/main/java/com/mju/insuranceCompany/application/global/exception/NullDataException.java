package com.mju.insuranceCompany.application.global.exception;

public class NullDataException extends MyException{
    public NullDataException() {
        super("Cannot find data!");
    }
}
