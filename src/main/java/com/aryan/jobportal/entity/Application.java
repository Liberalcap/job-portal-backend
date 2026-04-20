package com.aryan.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // who applied
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // which job
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    private String status; // APPLIED / SHORTLISTED / REJECTED
}