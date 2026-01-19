package com.mhong.jobtracker.dto.request;

public class UserLoginRequest {

    private String email;
    private String password;

    // No-args constructor
    public UserLoginRequest(){}

    public UserLoginRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

    // Getters and setters

    public String getEmail() { return email; }
    public void setEmail() { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword() { this.password = password; }

}
