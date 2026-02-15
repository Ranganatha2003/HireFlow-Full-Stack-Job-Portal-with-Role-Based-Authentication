package com.ranga.hireflow.repository;

import com.ranga.hireflow.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
