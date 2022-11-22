package com.mju.insuranceCompany.global.exception;

public class NullDataException extends RuntimeException{
    public NullDataException() {
        super("Cannot find data!");
    }
}
