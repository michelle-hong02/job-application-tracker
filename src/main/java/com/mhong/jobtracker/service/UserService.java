package com.mhong.jobtracker.service;

import com.mhong.jobtracker.domain.User;
import com.mhong.jobtracker.dto.request.UserLoginRequest;
import com.mhong.jobtracker.dto.request.UserRegistrationRequest;
import com.mhong.jobtracker.dto.response.UserRegistrationResponse;
import com.mhong.jobtracker.exception.EmailNotAvailableException;
import com.mhong.jobtracker.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder encoder;

    // injecting repository
    private final UserRepository repo;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
//        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {

        // Check if email exists
        if(repo.existsByEmail(request.getEmail())) {
            throw new EmailNotAvailableException(request.getEmail());
        }

        // Hash the raw password
        String hashPassword = encoder.encode(request.getPassword());

        User user = new User(request.getFirstName(), request.getLastName(), request.getEmail(), hashPassword);

        repo.save(user);

        return new UserRegistrationResponse(user.getEmail(), user.getFirstName());
    }

    public User authenticate(UserLoginRequest request) {

        // Find user by email
        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Incorrect email or password. Please try again."));

        // Extract salt from the hash, re-hashed the request password, compares internally
        if(!encoder.matches(request.getPassword(), user.getPasswordHash())){
            throw new BadCredentialsException("Incorrect email or password. Please try again.");
        }

        return user;

    }
}

