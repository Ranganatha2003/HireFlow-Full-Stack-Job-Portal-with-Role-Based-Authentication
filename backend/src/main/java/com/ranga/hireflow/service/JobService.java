package com.ranga.hireflow.service;

import com.ranga.hireflow.model.Job;
import com.ranga.hireflow.repository.ApplicationRepository;
import com.ranga.hireflow.repository.JobRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
private ApplicationRepository applicationRepository;


    // ➕ ADD JOB
    public Job addJob(Job job) {
        return jobRepository.save(job);
    }

    // 📄 GET ALL JOBS
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // 🔍 GET JOB BY ID
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
    }

    // ✏️ UPDATE JOB
    public Job updateJob(Long id, Job updatedJob) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));

        job.setTitle(updatedJob.getTitle());
        job.setCompany(updatedJob.getCompany());
        job.setLocation(updatedJob.getLocation());
        job.setSalary(updatedJob.getSalary());
        job.setDescription(updatedJob.getDescription());
        job.setSkillsRequired(updatedJob.getSkillsRequired());

        return jobRepository.save(job);
    }

    // ❌ DELETE JOB
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
    public List<Job> getJobsForUser(String email) {

    List<Job> jobs = jobRepository.findAll();

    List<Long> appliedJobIds =
            applicationRepository.findByApplicantEmail(email)
                                 .stream()
                                 .map(app -> app.getJob().getId())
                                 .toList();

    jobs.forEach(job -> {
        if (appliedJobIds.contains(job.getId())) {
            job.setApplied(true);
        }
        System.out.println("JOB " + job.getId() + " APPLIED = " + job.isApplied());
    });

    return jobs;
}

}
