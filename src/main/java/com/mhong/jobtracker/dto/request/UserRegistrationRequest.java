package com.mhong.jobtracker.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UserRegistrationRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    // No-args constructor
    public UserRegistrationRequest() {}

    // All-args constructor
    public UserRegistrationRequest(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    // Note: Spring requires setters to populate the object when deserializing JSON from a request body
    public String getFirstName() { return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

}
