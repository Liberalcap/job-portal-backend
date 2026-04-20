package com.aryan.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jobs")
public class Job {

    @Id  // 🔥 VERY IMPORTANT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String company;
    private String location;
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
}