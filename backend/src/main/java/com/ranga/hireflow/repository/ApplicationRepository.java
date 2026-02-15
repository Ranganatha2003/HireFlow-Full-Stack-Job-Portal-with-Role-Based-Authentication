package com.ranga.hireflow.repository;

import com.ranga.hireflow.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByApplicantEmail(String email);

    boolean existsByApplicantEmailAndJobId(String applicantEmail, Long jobId);
}
