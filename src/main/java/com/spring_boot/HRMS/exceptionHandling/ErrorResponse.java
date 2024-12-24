package com.spring_boot.HRMS.exceptionHandling;

public class ErrorResponse {
    private String message;
    private int statusCode;
    private long timeStamp;

    //constructor

    public ErrorResponse() {
    }

    public ErrorResponse(String message, int statusCode, long timeStamp) {
        this.message = message;
        this.statusCode = statusCode;
        this.timeStamp = timeStamp;
    }

    //setter and getter

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
