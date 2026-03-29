package com.mhong.jobtracker.exception;

public class ApiError {

    private int status;
    private String error;
    private String message;
    private long timestamp;

    public ApiError(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}
