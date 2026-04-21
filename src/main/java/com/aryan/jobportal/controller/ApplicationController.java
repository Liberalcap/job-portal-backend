package com.aryan.jobportal.controller;

import com.aryan.jobportal.dto.ApplicationResponse;
import com.aryan.jobportal.entity.Application;
import com.aryan.jobportal.service.ApplicationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:5173") //for backend and frontend
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }


    // 🔥 Apply to job
    @PostMapping("/{jobId}")
    public ApplicationResponse apply(@PathVariable Long jobId,
                                     Authentication authentication) {

        String email = authentication.getName(); // ✅ real user
        return applicationService.applyToJob(jobId, email);
    }
    @GetMapping("/my")
    public List<ApplicationResponse> getMyApplications(Authentication authentication) {

        String email = authentication.getName();

        return applicationService.getApplicationsByEmail(email);
    }

    @GetMapping("/job/{jobId}")
    public List<ApplicationResponse> getApplicationsByJob(@PathVariable Long jobId) {
        return applicationService.getApplicationsByJob(jobId);
    }

    @PutMapping("/{id}/status")
    public ApplicationResponse updateStatus(@PathVariable Long id,
                                            @RequestParam String status) {

        return applicationService.updateStatus(id, status);
    }
}