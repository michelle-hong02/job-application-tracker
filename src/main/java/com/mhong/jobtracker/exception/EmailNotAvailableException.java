package com.mhong.jobtracker.exception;

public class EmailNotAvailableException extends RuntimeException {

    public EmailNotAvailableException(String email){
        super("Email " + email + " is already taken");
    }
}
