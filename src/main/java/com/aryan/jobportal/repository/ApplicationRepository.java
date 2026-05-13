package com.aryan.jobportal.repository;

import com.aryan.jobportal.entity.Application;
import com.aryan.jobportal.entity.Job;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByUserId(Long userId);

    List<Application> findByJobId(Long jobId);

    // ✅ delete all applications related to a job
    void deleteByJob(Job job);

    // ✅ duplicate application checks
    boolean existsByUserIdAndJobId(Long userId, Long jobId);

    boolean existsByUserEmailAndJobId(String email, Long jobId);
}