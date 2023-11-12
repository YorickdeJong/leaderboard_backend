package com.example.java_test.service;

import com.example.java_test.model.Users;
import com.example.java_test.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }

    // Other business methods
}