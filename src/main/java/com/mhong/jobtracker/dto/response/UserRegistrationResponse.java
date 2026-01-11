package com.mhong.jobtracker.dto.response;

public class UserRegistrationResponse {

    private final String email;
    private final String firstName;

    // All-args constructor
    public UserRegistrationResponse(String email, String firstName) {
        this.email = email;
        this.firstName = firstName;
    }

    // Getters only
    // Note: You need getters to serialize the object to JSON ins repsonses
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
}
