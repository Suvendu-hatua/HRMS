package com.spring_boot.HRMS.exceptionHandling;

public class ProfileNotFoundException extends RuntimeException{
    /**
     * @param message
     */
    public ProfileNotFoundException(String message) {
        super(message);
    }
}
