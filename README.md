# Job Portal - Spring Boot Application

A comprehensive job portal application built with Spring Boot and MySQL, enabling job seekers to find and apply for jobs, and employers to post job openings and manage applications.

## 🚀 Features

- **User Management**: Support for both Job Seekers and Employers
- **Job Posting**: Employers can create and manage job listings
- **Application System**: Job seekers can apply for jobs with cover letters
- **Status Tracking**: Track application status (Pending, Reviewed, Shortlisted, etc.)

## 🛠️ Technologies Used

- **Backend**: Spring Boot 3.x
- **Database**: MySQL
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Java Version**: 17+

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6+
- IntelliJ IDEA (or any Java IDE)

## ⚙️ Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/job-portal-springboot.git
   cd job-portal-springboot
   ```

2. **Create MySQL Database**
   ```sql
   CREATE DATABASE job_portal;
   ```

3. **Configure Database**

   Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/job_portal
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   Or run directly from your IDE.

5. **Access the application**

   The application will start on `http://localhost:8080`

## 📊 Database Schema

### Entities

- **User**: Stores both Job Seekers and Employers
- **Job**: Job postings created by employers
- **Application**: Job applications submitted by job seekers

### Entity Relationships

- One User (Employer) can post many Jobs
- One User (Job Seeker) can have many Applications
- One Job can have many Applications

## 🔜 Upcoming Features

- REST API endpoints
- Authentication & Authorization
- Search and filter functionality
- Email notifications
- Resume upload functionality

## 👨‍💻 Author

Manish Rathi - Learning Spring Boot

## 📝 License

This project is for educational purposes.