package com.mhong.jobtracker.controller;

import com.mhong.jobtracker.dto.request.UserLoginRequest;
import com.mhong.jobtracker.dto.request.UserRegistrationRequest;
import com.mhong.jobtracker.dto.response.UserRegistrationResponse;
import com.mhong.jobtracker.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    // Register user
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        UserRegistrationResponse response = service.registerUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)// 201 Created
                .body(response);
    }

    // Login user
    // NOTE: right now login is only doing authentication not authorization
    // so RIGHT NOW returning 200 OK wiht no body is fine
    // nothing meaningful to return until you issue a JWT
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody UserLoginRequest request) {
        service.authenticate(request);
        return ResponseEntity
                .ok()
                .build();
        // todo for later:
        //     String token = authService.login(request);
        //    return ResponseEntity.ok(new LoginResponse(token));
    }
}
