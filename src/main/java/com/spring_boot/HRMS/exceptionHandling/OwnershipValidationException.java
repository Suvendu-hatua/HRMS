package com.spring_boot.HRMS.exceptionHandling;

public class OwnershipValidationException extends  RuntimeException{

    public OwnershipValidationException(String message) {
        super(message);
    }
}
