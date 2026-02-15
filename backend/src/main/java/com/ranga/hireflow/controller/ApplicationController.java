package com.ranga.hireflow.controller;

import com.ranga.hireflow.model.Application;
import com.ranga.hireflow.service.ApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // 👤 APPLY FOR JOB
    @PostMapping("/user/apply/{jobId}")
public Application apply(@PathVariable Long jobId,
                         @RequestParam String resumeUrl,
                         Authentication authentication) {

    String email = authentication.getName();

    return applicationService.applyJob(jobId, email, resumeUrl);
}

    // 👤 MY APPLICATIONS
    @GetMapping("/user/applications")
    public List<Application> myApplications(Authentication authentication) {

        String email = authentication.getName();

        return applicationService.getMyApplications(email);
    }

    // 👑 ADMIN → ALL APPLICATIONS
    @GetMapping("/admin/applications")
    public List<Application> getAll() {
        return applicationService.getAllApplications();
    }

    // 👑 ADMIN → UPDATE STATUS
    @PutMapping("/admin/applications/{id}")
    public Application updateStatus(@PathVariable Long id,
                                    @RequestParam String status) {

        return applicationService.updateStatus(id, status);
    }
}
