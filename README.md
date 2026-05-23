# Job Portal Backend

Backend for a full-stack Job Portal web application built using Spring Boot, Spring Security, JWT Authentication, and MySQL.

This backend provides REST APIs for authentication, job management, applications, recruiter dashboards, and user management.

---

## Live API

**Backend Deployment:** https://job-portal-backend-rvzr.onrender.com

**Frontend Repository:** https://github.com/Liberalcap/job-portal-frontend

---

## Features

### Authentication & Security

- JWT-based authentication
- Spring Security integration
- Role-based authorization
- Secure protected APIs
- Forgot password & reset password support

### Job Management

- Create job postings
- Edit existing jobs
- Fetch all jobs
- Fetch individual job details
- Delete jobs

### Application Management

- Apply for jobs
- View applied jobs
- Recruiter access to applicants

### User Management

- User registration & login
- Recruiter registration & login
- User role handling
- Protected recruiter routes

---

## Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- MySQL
- Maven
- Docker
- Render

---

## Project Architecture

The backend follows a layered Spring Boot architecture:

- Controller Layer → Handles REST API requests
- Service Layer → Business logic
- Repository Layer → Database access
- DTO Layer → Request/response handling
- Security Layer → JWT & authentication logic
- Config Layer → Application configuration

---

## Folder Structure

```bash
jobportal-backend/
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── aryan/
│   │   │           └── jobportal/
│   │   │
│   │   │               ├── config/
│   │   │               │   ├── CorsConfig.java
│   │   │               │   └── DataLoader.java
│   │   │               │
│   │   │               ├── controller/
│   │   │               │   ├── ApplicationController.java
│   │   │               │   ├── AuthController.java
│   │   │               │   ├── JobController.java
│   │   │               │   └── UserController.java
│   │   │               │
│   │   │               ├── dto/
│   │   │               │   ├── ApplicationResponse.java
│   │   │               │   ├── AuthRequest.java
│   │   │               │   ├── AuthResponse.java
│   │   │               │   └── JobResponse.java
│   │   │               │
│   │   │               ├── entity/
│   │   │               │   ├── Application.java
│   │   │               │   ├── Job.java
│   │   │               │   ├── PasswordResetToken.java
│   │   │               │   └── User.java
│   │   │               │
│   │   │               ├── repository/
│   │   │               │   ├── ApplicationRepository.java
│   │   │               │   ├── JobRepository.java
│   │   │               │   ├── PasswordResetTokenRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               │
│   │   │               ├── security/
│   │   │               │   ├── CustomUserDetailsService.java
│   │   │               │   ├── JwtAuthenticationFilter.java
│   │   │               │   ├── JwtService.java
│   │   │               │   └── SecurityConfig.java
│   │   │               │
│   │   │               ├── service/
│   │   │               │   ├── ApplicationService.java
│   │   │               │   ├── JobService.java
│   │   │               │   └── UserService.java
│   │   │               │
│   │   │               └── JobportalApplication.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│
├── target/
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

## Installation & Setup

### 1. Clone Repository

```bash
git clone https://github.com/Liberalcap/job-portal-backend.git
cd job-portal-backend
```

---

### 2. Configure Environment Variables

Configure the following environment variables:

```env
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
APP_JWT_SECRET=
APP_JWT_EXPIRATION_MS=
```

---

### 3. Run Application

```bash
./mvnw spring-boot:run
```

Application runs on:

```bash
http://localhost:8080
```

---

## API Endpoints

### Authentication APIs

- POST `/auth/register`
- POST `/auth/login`
- POST `/auth/forgot-password`
- POST `/auth/reset-password`

### Job APIs

- GET `/jobs`
- GET `/jobs/{id}`
- POST `/jobs`
- PUT `/jobs/{id}`
- DELETE `/jobs/{id}`

### Application APIs

- POST `/applications/apply`
- GET `/applications/my-applications`

### User APIs

- GET `/users`
- GET `/users/{id}`

---

## Security Features

- JWT token authentication
- Role-based authorization
- Protected recruiter APIs
- Password encryption
- Secure API access

---

## Deployment

- Backend deployed using Render
- Docker support included using Dockerfile

---

## Author

Aryan Dubey

- GitHub: https://github.com/Liberalcap