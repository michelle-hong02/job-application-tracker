package com.mhong.jobtracker.dto.request;

public class UserLoginRequest {

    private String username;
    private String password;

    // No-args constructor
    public UserLoginRequest(){}

    public UserLoginRequest(String username, String password){
        this.username = username;
        this.password = password;
    }

    // Getters and setters

    public String getUsername() { return username; }
    public void setUsername() { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword() { this.password = password; }

}
