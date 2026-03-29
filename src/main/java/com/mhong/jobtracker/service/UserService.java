package com.mhong.jobtracker.service;

import com.mhong.jobtracker.domain.User;
import com.mhong.jobtracker.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private final UserRepository repo;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User getUserByUsername(String username) {
        log.info("Fetching user by username={}", username);

        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        log.info("Successfully fetched user username={}", username);

        return user;
    }
}
