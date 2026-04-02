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

    // Getters for Spring json serialization
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
}
