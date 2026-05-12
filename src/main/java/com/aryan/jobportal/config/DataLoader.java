package com.aryan.jobportal.config;

import com.aryan.jobportal.entity.Job;
import com.aryan.jobportal.entity.User;
import com.aryan.jobportal.repository.JobRepository;
import com.aryan.jobportal.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public DataLoader(JobRepository jobRepository,
                      UserRepository userRepository) {

        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User recruiter = userRepository.findByEmail("aryandubey055@gmail.com")
                .orElse(null);

        if (recruiter == null) {
            System.out.println("Recruiter not found");
            return;
        }

        // Check if demo jobs already exist
        boolean googleExists = jobRepository
                .findAll()
                .stream()
                .anyMatch(job -> "Google".equals(job.getCompany()));

        if (!googleExists) {

            Job job1 = new Job();
            job1.setTitle("Software Engineer");
            job1.setCompany("Google");
            job1.setLocation("Bangalore");
            job1.setSalary(25.0);
            job1.setDescription("Java Spring Boot backend development");
            job1.setPostedBy(recruiter.getEmail());
            job1.setCreatedBy(recruiter);

            Job job2 = new Job();
            job2.setTitle("Frontend Developer");
            job2.setCompany("Amazon");
            job2.setLocation("Hyderabad");
            job2.setSalary(18.0);
            job2.setDescription("React frontend development");
            job2.setPostedBy(recruiter.getEmail());
            job2.setCreatedBy(recruiter);

            Job job3 = new Job();
            job3.setTitle("Full Stack Developer");
            job3.setCompany("Microsoft");
            job3.setLocation("Pune");
            job3.setSalary(20.0);
            job3.setDescription("React + Spring Boot full stack development");
            job3.setPostedBy(recruiter.getEmail());
            job3.setCreatedBy(recruiter);

            Job job4 = new Job();
            job1.setTitle("Software Developer");
            job1.setCompany("J P Morgan");
            job1.setLocation("Bangalore");
            job1.setSalary(24.0);
            job1.setDescription("Java Spring Boot backend development");
            job1.setPostedBy(recruiter.getEmail());
            job1.setCreatedBy(recruiter);

            Job job5 = new Job();
            job1.setTitle("UI-UX ");
            job1.setCompany("Mancraft");
            job1.setLocation("Mumbai");
            job1.setSalary(12.0);
            job1.setDescription("Experienced UI-UX Developer");
            job1.setPostedBy(recruiter.getEmail());
            job1.setCreatedBy(recruiter);

            Job job6 = new Job();
            job1.setTitle("Software Engineer");
            job1.setCompany("Air India");
            job1.setLocation("Gurgaon");
            job1.setSalary(28.0);
            job1.setDescription("Java Spring Boot backend development");
            job1.setPostedBy(recruiter.getEmail());
            job1.setCreatedBy(recruiter);

            jobRepository.save(job1);
            jobRepository.save(job2);
            jobRepository.save(job3);
            jobRepository.save(job4);
            jobRepository.save(job5);
            jobRepository.save(job6);


            System.out.println("Demo jobs inserted successfully!");
        }
    }
}