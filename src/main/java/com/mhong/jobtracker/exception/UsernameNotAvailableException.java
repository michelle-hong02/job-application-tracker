package com.mhong.jobtracker.exception;

public class UsernameNotAvailableException extends RuntimeException {

    public UsernameNotAvailableException(String username){
        super("Username " + username + " is already taken");
    }
}
