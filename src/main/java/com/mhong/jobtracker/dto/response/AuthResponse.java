package com.mhong.jobtracker.dto.response;

public class AuthResponse {

    private final String token;
    private final String username;

    // All-args constructor
    public AuthResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    // Getters only
    // Note: You need getters to serialize the object to JSON ins responses

    public String getToken() { return token;}
    public String getUsername() { return username; }
}
