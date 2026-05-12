package com.aryan.jobportal.controller;

import com.aryan.jobportal.dto.JobResponse;
import com.aryan.jobportal.entity.Job;
import com.aryan.jobportal.service.JobService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
//@CrossOrigin(origins = "https://job-portal-frontend-mwadfn8vx-liberalcaps-projects.vercel.app")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // CREATE JOB
    @PostMapping
    public Job createJob(@RequestBody Job job,
                         Authentication authentication) {

        String email = authentication.getName();
        return jobService.createJob(job, email);
    }

    // GET ALL JOBS
    @GetMapping
    public List<JobResponse> getAllJobs() {
        return jobService.getAllJobs();
    }

    // GET JOB BY ID
    @GetMapping("/{id}")
    public JobResponse getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    // GET RECRUITER JOBS
    @GetMapping("/my")
    public List<JobResponse> getMyJobs(Authentication authentication) {

        String email = authentication.getName();

        return jobService.getJobsByRecruiter(email);
    }

    // UPDATE JOB
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(
            @PathVariable Long id,
            @RequestBody Job updatedJob,
            Authentication authentication
    ) {

        String email = authentication.getName();

        Job job = jobService.updateJob(id, updatedJob, email);

        return ResponseEntity.ok(job);
    }

    // DELETE JOB
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id,
                          Authentication authentication) {

        String email = authentication.getName();

        jobService.deleteJob(id, email);
    }
}