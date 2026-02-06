package com.mhong.jobtracker.controller;

import com.mhong.jobtracker.dto.request.UserLoginRequest;
import com.mhong.jobtracker.dto.request.UserRegistrationRequest;
import com.mhong.jobtracker.dto.response.AuthResponse;
import com.mhong.jobtracker.security.JwtService;
import com.mhong.jobtracker.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    private final JwtService jwtService;

    public AuthController(AuthService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    // Register user
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        AuthResponse response = service.registerUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)// 201 Created
                .body(response);
    }

    // Login user
    // NOTE: right now login is only doing authentication not authorization
    // so RIGHT NOW returning 200 OK wiht no body is fine
    // nothing meaningful to return until you issue a JWT
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody UserLoginRequest request) {

        Authentication authentication = service.authenticate(request);

        UserDetails user = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(user.getUsername());

        return ResponseEntity.ok(
                new AuthResponse(token, authentication.getName())
        );
    }
}
