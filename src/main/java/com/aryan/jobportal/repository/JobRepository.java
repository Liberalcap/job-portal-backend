package com.aryan.jobportal.repository;

import com.aryan.jobportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JobRepository extends JpaRepository<Job, Long> {

    // ✅ FIXED: use relation instead of string field
    List<Job> findByCreatedBy_Email(String email);
}