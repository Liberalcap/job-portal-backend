package com.aryan.jobportal.controller;

import com.aryan.jobportal.dto.JobResponse; // ✅ ADD THIS
import com.aryan.jobportal.entity.Job;
import com.aryan.jobportal.service.JobService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
//@CrossOrigin(origins = "https://job-portal-frontend-mwadfn8vx-liberalcaps-projects.vercel.app")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public Job createJob(@RequestBody Job job,
                         Authentication authentication) {

        String email = authentication.getName();
        return jobService.createJob(job, email);
    }

    @GetMapping
    public List<JobResponse> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/{id}")
    public JobResponse getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @GetMapping("/my")
    public List<JobResponse> getMyJobs(Authentication authentication) {
        String email = authentication.getName();
        return jobService.getJobsByRecruiter(email);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id, Authentication authentication) {

        String email = authentication.getName();

        jobService.deleteJob(id, email);
    }
}