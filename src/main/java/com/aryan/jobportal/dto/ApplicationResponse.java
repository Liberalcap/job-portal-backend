package com.aryan.jobportal.dto;

import lombok.Data;

@Data
public class ApplicationResponse {

    private Long id;
    private String userEmail;
    private String jobTitle;
    private String status;
}