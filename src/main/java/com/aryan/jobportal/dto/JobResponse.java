package com.aryan.jobportal.dto;

import lombok.Data;

@Data
public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private String company;
    private String location;
    private Double salary;

    // only safe user info
    private String createdByEmail;
}