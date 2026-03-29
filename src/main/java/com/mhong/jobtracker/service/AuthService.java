package com.mhong.jobtracker.service;

import com.mhong.jobtracker.domain.User;
import com.mhong.jobtracker.dto.request.UserLoginRequest;
import com.mhong.jobtracker.dto.request.UserRegistrationRequest;
import com.mhong.jobtracker.dto.response.AuthResponse;
import com.mhong.jobtracker.exception.UsernameNotAvailableException;
import com.mhong.jobtracker.repository.UserRepository;
import com.mhong.jobtracker.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserRepository repo, PasswordEncoder encoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.repo = repo;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse registerUser(UserRegistrationRequest request) {

        log.info("Registering user...");
        log.info("Checking if username is available");

        // Check if user exists
        if(repo.existsByUsername(request.getUsername())) {
            log.error("Username {} is not available", request.getUsername());
            throw new UsernameNotAvailableException(request.getUsername());
        }

        log.info("Registering user: username={}, firstName={}, lastName={}",
                request.getUsername(), request.getFirstName(), request.getLastName());
        // Hash the raw password
        String hashPassword = encoder.encode(request.getPassword());

        User user = new User(request.getFirstName(), request.getLastName(), request.getUsername(), hashPassword);

        repo.save(user);

        String token = jwtService.generateToken(user.getUsername());
        log.info("JWT generated for username={}", user.getUsername());

        log.info("User {} successfully registered", user.getUsername());

        return new AuthResponse(token, user.getUsername());
    }

    public Authentication authenticate(UserLoginRequest request) {
        try {

            log.info("Authenticating credentials...");

            // Spring calls UserDetailsService, checks password
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            // When authentication succeeds
            log.info("User {} successfully authenticated", request.getUsername());

            return auth;

        } catch (AuthenticationException ex) {

            log.warn("Failed login attempt username={}", request.getUsername());
            // Wrap Spring Security exceptions in a clean BadCredentialsException
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}