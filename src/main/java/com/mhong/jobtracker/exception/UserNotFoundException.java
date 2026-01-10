package com.mhong.jobtracker.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}
