package com.mhong.jobtracker.exception;

public class ApplicationNotFoundException extends RuntimeException{

    public ApplicationNotFoundException(Long id) {
        super("Could not find application " + id);
    }
}
