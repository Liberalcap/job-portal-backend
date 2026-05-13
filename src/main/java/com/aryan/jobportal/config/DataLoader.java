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

        User recruiter = userRepository
                .findByEmail("aryandubey055@gmail.com")
                .orElse(null);

        if (recruiter == null) {
            System.out.println("Recruiter not found");
            return;
        }

        insertJobIfNotExists(
                "Google",
                "Software Engineer",
                "Bangalore",
                25.0,
                "Java Spring Boot backend development",
                recruiter
        );

        insertJobIfNotExists(
                "Amazon",
                "Frontend Developer",
                "Hyderabad",
                18.0,
                "React frontend development",
                recruiter
        );

        insertJobIfNotExists(
                "Microsoft",
                "Full Stack Developer",
                "Pune",
                20.0,
                "React + Spring Boot full stack development",
                recruiter
        );

        insertJobIfNotExists(
                "J P Morgan",
                "Software Developer",
                "Bangalore",
                24.0,
                "Java Spring Boot backend development",
                recruiter
        );

        insertJobIfNotExists(
                "Mancraft",
                "UI-UX Designer",
                "Mumbai",
                12.0,
                "Experienced UI-UX Developer",
                recruiter
        );

        insertJobIfNotExists(
                "Air India",
                "Software Engineer",
                "Gurgaon",
                28.0,
                "Java Spring Boot backend development",
                recruiter
        );

        System.out.println("Demo jobs checked/inserted successfully!");
    }

    private void insertJobIfNotExists(
            String company,
            String title,
            String location,
            Double salary,
            String description,
            User recruiter
    ) {

        boolean exists = jobRepository.findAll()
                .stream()
                .anyMatch(job -> company.equals(job.getCompany()));

        if (!exists) {

            Job job = new Job();

            job.setTitle(title);
            job.setCompany(company);
            job.setLocation(location);
            job.setSalary(salary);
            job.setDescription(description);
            job.setPostedBy(recruiter.getEmail());
            job.setCreatedBy(recruiter);

            jobRepository.save(job);

            System.out.println(company + " job inserted");
        }
    }
}