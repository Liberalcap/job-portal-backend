# Job Portal Backend

Backend for a full-stack Job Portal web application built using Spring Boot, Spring Security, JWT Authentication, Spring Data JPA, and PostgreSQL.

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
- User role handling
- Secure protected APIs
- Forgot password & reset password token support

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
- Role-based user management
- Protected authenticated routes

---

## Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- PostgreSQL
- Maven
- Docker
- Render

---

## Project Architecture

The backend follows a layered Spring Boot architecture:

- Controller Layer -> Handles REST API requests
- Service Layer -> Business logic
- Repository Layer -> Database access
- DTO Layer -> Request/response handling
- Security Layer -> JWT & authentication logic
- Config Layer -> Application configuration

---

## Folder Structure

```bash
jobportal-backend/
|-- .mvn/
|   `-- wrapper/
|       `-- maven-wrapper.properties
|
|-- src/
|   |-- main/
|   |   |-- java/
|   |   |   `-- com/
|   |   |       `-- aryan/
|   |   |           `-- jobportal/
|   |   |               |-- config/
|   |   |               |   |-- CorsConfig.java
|   |   |               |   `-- DataLoader.java
|   |   |               |
|   |   |               |-- controller/
|   |   |               |   |-- ApplicationController.java
|   |   |               |   |-- AuthController.java
|   |   |               |   |-- JobController.java
|   |   |               |   `-- UserController.java
|   |   |               |
|   |   |               |-- dto/
|   |   |               |   |-- ApplicationResponse.java
|   |   |               |   |-- AuthRequest.java
|   |   |               |   |-- AuthResponse.java
|   |   |               |   `-- JobResponse.java
|   |   |               |
|   |   |               |-- entity/
|   |   |               |   |-- Application.java
|   |   |               |   |-- Job.java
|   |   |               |   |-- PasswordResetToken.java
|   |   |               |   `-- User.java
|   |   |               |
|   |   |               |-- repository/
|   |   |               |   |-- ApplicationRepository.java
|   |   |               |   |-- JobRepository.java
|   |   |               |   |-- PasswordResetTokenRepository.java
|   |   |               |   `-- UserRepository.java
|   |   |               |
|   |   |               |-- security/
|   |   |               |   |-- CustomUserDetailsService.java
|   |   |               |   |-- JwtAuthenticationFilter.java
|   |   |               |   |-- JwtService.java
|   |   |               |   `-- SecurityConfig.java
|   |   |               |
|   |   |               |-- service/
|   |   |               |   |-- ApplicationService.java
|   |   |               |   |-- JobService.java
|   |   |               |   `-- UserService.java
|   |   |               |
|   |   |               `-- JobportalApplication.java
|   |   |
|   |   `-- resources/
|   |       |-- static/
|   |       |-- templates/
|   |       `-- application.properties
|   |
|   `-- test/
|       `-- java/
|           `-- com/
|               `-- aryan/
|                   `-- jobportal/
|                       `-- JobportalApplicationTests.java
|
|-- .gitignore
|-- Dockerfile
|-- mvnw
|-- mvnw.cmd
|-- pom.xml
`-- README.md
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
SPRING_DATASOURCE_URL=jdbc:postgresql://...
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password
APP_JWT_SECRET=your_jwt_secret
APP_JWT_EXPIRATION_MS=86400000
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

- POST `/api/auth/register`
- POST `/api/auth/login`
- POST `/api/auth/forgot-password`
- POST `/api/auth/reset-password`

### Job APIs

- GET `/api/jobs`
- GET `/api/jobs/{id}`
- GET `/api/jobs/my`
- POST `/api/jobs`
- PUT `/api/jobs/{id}`
- DELETE `/api/jobs/{id}`

### Application APIs

- POST `/api/applications/{jobId}`
- GET `/api/applications/check/{jobId}`
- GET `/api/applications/my`
- GET `/api/applications/job/{jobId}`
- PUT `/api/applications/{id}/status?status=APPLIED`

### User APIs

- POST `/users/register`
- GET `/users`
- GET `/users/{id}`
- PUT `/users/{id}`
- DELETE `/users/{id}`

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

**Aryan Dubey**

- GitHub: https://github.com/Liberalcap
