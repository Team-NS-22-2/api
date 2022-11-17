package com.mju.insuranceCompany.global.exception;

public class NullDataException extends MyException{
    public NullDataException() {
        super("Cannot find data!");
    }
}
