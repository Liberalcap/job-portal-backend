package com.aryan.jobportal.service;

import com.aryan.jobportal.dto.JobResponse;
import com.aryan.jobportal.entity.Job;
import com.aryan.jobportal.entity.User;
import com.aryan.jobportal.repository.ApplicationRepository;
import com.aryan.jobportal.repository.JobRepository;
import com.aryan.jobportal.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public JobService(JobRepository jobRepository,
                      UserRepository userRepository,
                      ApplicationRepository applicationRepository) {

        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
    }

    // ✅ CREATE JOB
    public Job createJob(Job job, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        job.setPostedBy(email);
        job.setCreatedBy(user);

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

    // ✅ DELETE JOB
    @Transactional
    public void deleteJob(Long jobId, String email) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // 🔥 handle old data
        if (job.getCreatedBy() == null) {

            applicationRepository.deleteByJob(job);

            jobRepository.delete(job);

            return;
        }

        // ✅ recruiter ownership check
        if (!job.getCreatedBy().getEmail().equals(email)) {
            throw new RuntimeException("Not allowed");
        }

        // ✅ delete related applications first
        applicationRepository.deleteByJob(job);

        // ✅ then delete job
        jobRepository.delete(job);
    }

    // ✅ GET JOBS BY RECRUITER
    public List<JobResponse> getJobsByRecruiter(String email) {

        return jobRepository.findByCreatedBy_Email(email)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ UPDATE JOB
    public Job updateJob(Long jobId, Job updatedJob, String email) {

        Job existingJob = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // 🔥 handle old data
        if (existingJob.getCreatedBy() == null) {

            existingJob.setTitle(updatedJob.getTitle());
            existingJob.setDescription(updatedJob.getDescription());
            existingJob.setCompany(updatedJob.getCompany());
            existingJob.setLocation(updatedJob.getLocation());
            existingJob.setSalary(updatedJob.getSalary());

            return jobRepository.save(existingJob);
        }

        // ✅ recruiter ownership check
        if (!existingJob.getCreatedBy().getEmail().equals(email)) {
            throw new RuntimeException("Not allowed");
        }

        // ✅ update fields
        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setCompany(updatedJob.getCompany());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setSalary(updatedJob.getSalary());

        return jobRepository.save(existingJob);
    }

    // ✅ DTO MAPPER
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