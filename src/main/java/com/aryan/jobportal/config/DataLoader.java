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
            job4.setTitle("Software Developer");
            job4.setCompany("J P Morgan");
            job4.setLocation("Bangalore");
            job4.setSalary(24.0);
            job4.setDescription("Java Spring Boot backend development");
            job4.setPostedBy(recruiter.getEmail());
            job4.setCreatedBy(recruiter);

            Job job5 = new Job();
            job5.setTitle("UI-UX");
            job5.setCompany("Mancraft");
            job5.setLocation("Mumbai");
            job5.setSalary(12.0);
            job5.setDescription("Experienced UI-UX Developer");
            job5.setPostedBy(recruiter.getEmail());
            job5.setCreatedBy(recruiter);

            Job job6 = new Job();
            job6.setTitle("Software Engineer");
            job6.setCompany("Air India");
            job6.setLocation("Gurgaon");
            job6.setSalary(28.0);
            job6.setDescription("Java Spring Boot backend development");
            job6.setPostedBy(recruiter.getEmail());
            job6.setCreatedBy(recruiter);

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