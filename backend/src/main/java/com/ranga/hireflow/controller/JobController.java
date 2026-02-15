package com.ranga.hireflow.controller;

import com.ranga.hireflow.model.Job;
import com.ranga.hireflow.service.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class JobController {

    @Autowired
    private JobService jobService;

    // 👑 ADMIN → ADD JOB
    @PostMapping("/admin/jobs")
    public Job addJob(@RequestBody Job job) {
        return jobService.addJob(job);
    }

    // 👤 VIEW JOBS (PUBLIC + LOGGED-IN USER SUPPORT)
    @GetMapping("/jobs")
    public List<Job> getAllJobs(Authentication authentication) {

        // 🔓 If not logged in → normal job list
        if (authentication == null || !authentication.isAuthenticated()) {
            return jobService.getAllJobs();
        }

        // 👤 If logged in → return jobs with applied flag
        String email = authentication.getName();
        return jobService.getJobsForUser(email);
    }

    // 👤 VIEW JOB BY ID
    @GetMapping("/jobs/{id}")
    public Job getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    // 👑 ADMIN → UPDATE JOB
    @PutMapping("/admin/jobs/{id}")
    public Job updateJob(@PathVariable Long id, @RequestBody Job job) {
        return jobService.updateJob(id, job);
    }

    // 👑 ADMIN → DELETE JOB
    @DeleteMapping("/admin/jobs/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "Job deleted successfully";
    }
}
