package com.jobportal.service;


import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.enums.ApplicationStatus;
import com.jobportal.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    @Autowired
    private final ApplicationRepository applicationRepository;

    // Create or Update Application
    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }

    // Get all applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Get application by ID
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    // Get applications by applicant
    public List<Application> getApplicationsByApplicant(User applicant) {
        return applicationRepository.findByApplicant(applicant);
    }

    // Get applications by applicant ID
    public List<Application> getApplicationsByApplicantId(Long applicantId) {
        return applicationRepository.findByApplicantId(applicantId);
    }

    // Get applications by job
    public List<Application> getApplicationsByJob(Job job) {
        return applicationRepository.findByJob(job);
    }

    // Get applications by job ID
    public List<Application> getApplicationsByJobId(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    // Get applications by status
    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }

    // Get applications by job and status
    public List<Application> getApplicationsByJobAndStatus(Long jobId, ApplicationStatus status) {
        return applicationRepository.findByJobIdAndStatus(jobId, status);
    }

    // Get applications by applicant and status
    public List<Application> getApplicationsByApplicantAndStatus(Long applicantId, ApplicationStatus status) {
        return applicationRepository.findByApplicantIdAndStatus(applicantId, status);
    }

    // Check if user already applied for a job
    public boolean hasUserApplied(Long jobId, Long applicantId) {
        return applicationRepository.existsByJobIdAndApplicantId(jobId, applicantId);
    }

    // Get specific application by job and applicant
    public Optional<Application> getApplicationByJobAndApplicant(Long jobId, Long applicantId) {
        return applicationRepository.findByJobIdAndApplicantId(jobId, applicantId);
    }

    // Count applications for a job
    public long countApplicationsForJob(Long jobId) {
        return applicationRepository.countByJobId(jobId);
    }

    // Update application status
    public Application updateApplicationStatus(Long id, ApplicationStatus status) {
        Optional<Application> existingApplication = applicationRepository.findById(id);
        if (existingApplication.isPresent()) {
            Application application = existingApplication.get();
            application.setStatus(status);
            return applicationRepository.save(application);
        }
        return null;
    }

    // Add notes to application (by employer)
    public Application addNotesToApplication(Long id, String notes) {
        Optional<Application> existingApplication = applicationRepository.findById(id);
        if (existingApplication.isPresent()) {
            Application application = existingApplication.get();
            application.setNotes(notes);
            return applicationRepository.save(application);
        }
        return null;
    }

    // Delete application
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }
}
