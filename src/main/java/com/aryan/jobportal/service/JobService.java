package com.aryan.jobportal.service;

import com.aryan.jobportal.dto.JobResponse;
import com.aryan.jobportal.entity.Job;
import com.aryan.jobportal.entity.User;
import com.aryan.jobportal.repository.JobRepository;
import com.aryan.jobportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobService(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    // ✅ CREATE JOB (FIXED)
    public Job createJob(Job job, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        job.setCreatedBy(user); // ✅ IMPORTANT FIX

        return jobRepository.save(job);
    }

    // ✅ GET ALL JOBS
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ GET JOB BY ID
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        return mapToResponse(job);
    }

    // ✅ GET JOBS BY RECRUITER (FIXED)
    public List<JobResponse> getJobsByRecruiter(String email) {
        return jobRepository.findByCreatedBy_Email(email)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ SINGLE CLEAN MAPPER
    private JobResponse mapToResponse(Job job) {
        JobResponse dto = new JobResponse();

        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setCompany(job.getCompany());
        dto.setLocation(job.getLocation());
        dto.setSalary(job.getSalary());

        if (job.getCreatedBy() != null) {
            dto.setCreatedByEmail(job.getCreatedBy().getEmail());
        }

        return dto;
    }
}