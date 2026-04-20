package com.aryan.jobportal.service;

import com.aryan.jobportal.dto.JobResponse; // ✅ ADD THIS
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

    public Job createJob(Job job, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        job.setCreatedBy(user);
        return jobRepository.save(job);
    }

    // ✅ DTO version
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ✅ ALSO convert this to DTO
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        return mapToDTO(job);
    }

    // ✅ ADD THIS METHOD (VERY IMPORTANT)
    private JobResponse mapToDTO(Job job) {
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