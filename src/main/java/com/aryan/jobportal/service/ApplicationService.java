package com.aryan.jobportal.service;

import com.aryan.jobportal.dto.ApplicationResponse;
import com.aryan.jobportal.entity.Application;
import com.aryan.jobportal.entity.Job;
import com.aryan.jobportal.entity.User;
import com.aryan.jobportal.repository.ApplicationRepository;
import com.aryan.jobportal.repository.JobRepository;
import com.aryan.jobportal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              UserRepository userRepository,
                              JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public List<ApplicationResponse> getApplicationsByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return applicationRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ✅ APPLY TO JOB (UPDATED)
    public ApplicationResponse applyToJob(Long jobId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // 🚨 Prevent duplicate application
        boolean alreadyApplied = applicationRepository
                .existsByUserIdAndJobId(user.getId(), jobId);

        if (alreadyApplied) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You already applied for this job"
            );
        }

        Application application = new Application();
        application.setUser(user);
        application.setJob(job);
        application.setStatus("APPLIED");

        return mapToDTO(applicationRepository.save(application));
    }

    // ✅ GET USER APPLICATIONS (UPDATED)
    public List<ApplicationResponse> getUserApplications(Long userId) {
        return applicationRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<ApplicationResponse> getApplicationsByJob(Long jobId) {

        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public ApplicationResponse updateStatus(Long applicationId, String status) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(status);

        return mapToDTO(applicationRepository.save(application));
    }

    // ✅ MAPPING METHOD
    private ApplicationResponse mapToDTO(Application app) {
        ApplicationResponse dto = new ApplicationResponse();

        dto.setId(app.getId());
        dto.setStatus(app.getStatus());

        if (app.getUser() != null) {
            dto.setUserEmail(app.getUser().getEmail());
        }

        if (app.getJob() != null) {
            dto.setJobTitle(app.getJob().getTitle());
        }

        return dto;
    }
}