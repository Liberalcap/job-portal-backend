package com.aryan.jobportal.repository;

import com.aryan.jobportal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByUserId(Long userId);

    List<Application> findByJobId(Long jobId);

    // ✅ ADD THIS (for duplicate check)
    boolean existsByUserIdAndJobId(Long userId, Long jobId);
}