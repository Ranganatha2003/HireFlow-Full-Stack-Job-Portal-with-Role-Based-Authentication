package com.ranga.hireflow.service;

import com.ranga.hireflow.model.User;
import com.ranga.hireflow.repository.UserRepository;
import com.ranga.hireflow.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(User user) {

    System.out.println("Register called for: " + user.getEmail());

    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
        throw new RuntimeException("Email already exists");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    if (user.getRole() == null || user.getRole().isEmpty()) {
        user.setRole("USER");
    }

    User savedUser = userRepository.save(user);

    System.out.println("User saved successfully");

    return savedUser;
}


    public String login(String email, String password) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
        throw new RuntimeException("Invalid password");
    }

    return jwtUtil.generateToken(user.getEmail());
}


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
