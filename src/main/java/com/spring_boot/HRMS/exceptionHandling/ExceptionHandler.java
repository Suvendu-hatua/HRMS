package com.spring_boot.HRMS.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

    /**
     * @param exe
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorResponse> handleProfileNotFoundException(ProfileNotFoundException exe){
        //creating an instance of ErrorResponse pojo.
        ErrorResponse errorResponse=new ErrorResponse();

        //setting properties of ErrorResponse.
        errorResponse.setMessage(exe.getMessage());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        //returning object
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
    }

    /**
     * @param exe
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exe){
        //creating an instance of ErrorResponse pojo.
        ErrorResponse errorResponse=new ErrorResponse();

        //setting properties of ErrorResponse.
        errorResponse.setMessage(exe.getClass().getName()+":"+exe.getMessage());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        //returning object
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    // Handle AccessDeniedException
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleOwnershipValidationException(OwnershipValidationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.FORBIDDEN.value());
        response.put("error", "Forbidden");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
