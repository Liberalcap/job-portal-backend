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

        job.setPostedBy(email);
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

    public void deleteJob(Long jobId, String email) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // 🔥 handle old data (IMPORTANT)
        if (job.getCreatedBy() == null) {
            System.out.println("Old job detected (createdBy is null)");
            jobRepository.delete(job);
            return;
        }

        if (!job.getCreatedBy().getEmail().equals(email)) {
            throw new RuntimeException("Not allowed");
        }

        jobRepository.delete(job);
    }

    // ✅ GET JOBS BY RECRUITER (FIXED)
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