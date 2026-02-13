package com.ranga.hireflow.controller;

import com.ranga.hireflow.model.User;
import com.ranga.hireflow.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
public User register(@RequestBody User user) {
    System.out.println("REGISTER API CALLED → " + user.getEmail());
    return userService.register(user);
}


    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.login(user.getEmail(), user.getPassword());
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
}
