package com.mhong.jobtracker.service;

import com.mhong.jobtracker.domain.User;
import com.mhong.jobtracker.dto.request.UserRegistrationRequest;
import com.mhong.jobtracker.dto.response.UserRegistrationResponse;
import com.mhong.jobtracker.exception.EmailNotAvailableException;
import com.mhong.jobtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder encoder;

    // injecting repository
    private final UserRepository repo;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
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
}

