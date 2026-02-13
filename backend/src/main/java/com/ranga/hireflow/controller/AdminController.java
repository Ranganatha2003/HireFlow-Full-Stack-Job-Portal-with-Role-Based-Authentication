package com.ranga.hireflow.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/test")
    public String adminTest() {
        return "ADMIN API WORKING ✅";
    }
}
