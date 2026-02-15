package com.ranga.hireflow.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    @GetMapping("/api/debug")
    public Object debug(Authentication authentication) {
        return authentication;
    }
}
