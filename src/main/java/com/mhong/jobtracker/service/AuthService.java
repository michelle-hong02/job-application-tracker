package com.mhong.jobtracker.service;

import com.mhong.jobtracker.domain.User;
import com.mhong.jobtracker.dto.request.UserLoginRequest;
import com.mhong.jobtracker.dto.request.UserRegistrationRequest;
import com.mhong.jobtracker.dto.response.AuthResponse;
import com.mhong.jobtracker.exception.UsernameNotAvailableException;
import com.mhong.jobtracker.repository.UserRepository;
import com.mhong.jobtracker.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository repo, PasswordEncoder encoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.repo = repo;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse registerUser(UserRegistrationRequest request) {

        // Check if user exists
        if(repo.existsByUsername(request.getUsername())) {
            throw new UsernameNotAvailableException(request.getUsername());
        }

        // Hash the raw password
        String hashPassword = encoder.encode(request.getPassword());

        User user = new User(request.getFirstName(), request.getLastName(), request.getUsername(), hashPassword);

        repo.save(user);

        String token = jwtService.generateToken(user.getUsername());

        return new AuthResponse(token, user.getUsername());
    }

    public Authentication authenticate(UserLoginRequest request) {
        // Spring calls UserDetailsService, checks password
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
    }
}