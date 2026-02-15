package com.ranga.hireflow.service;

import com.ranga.hireflow.model.Application;
import com.ranga.hireflow.model.ApplicationStatus;
import com.ranga.hireflow.model.Job;
import com.ranga.hireflow.repository.ApplicationRepository;
import com.ranga.hireflow.repository.JobRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    // 👤 APPLY FOR JOB
    public Application applyJob(Long jobId, String email, String resumeUrl) {

        if (applicationRepository.existsByApplicantEmailAndJobId(email, jobId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "You have already applied for this job"
            );
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Application app = new Application();
        app.setApplicantEmail(email);
        app.setResumeUrl(resumeUrl);
        app.setJob(job);
        app.setStatus(ApplicationStatus.APPLIED);   // ✅ ENUM DEFAULT

        return applicationRepository.save(app);
    }

    // 👤 VIEW MY APPLICATIONS
    public List<Application> getMyApplications(String email) {
        return applicationRepository.findByApplicantEmail(email);
    }

    // 👑 ADMIN → VIEW ALL
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // 👑 ADMIN → UPDATE STATUS
    public Application updateStatus(Long id, String status) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(ApplicationStatus.valueOf(status));   // ✅ ENUM CONVERSION

        return applicationRepository.save(app);
    }
}
