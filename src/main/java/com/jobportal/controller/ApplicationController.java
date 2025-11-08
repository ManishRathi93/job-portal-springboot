package com.jobportal.controller;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.enums.ApplicationStatus;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    @Autowired
    private final ApplicationService applicationService;

    @Autowired
    private final JobService jobService;

    @Autowired
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> applyForJob(
            @RequestParam Long jobId,
            @RequestParam Long applicantId,
            @RequestParam(required = false) String coverLetter) {

        // Check if job exists
        Optional<Job> job = jobService.getJobById(jobId);
        if (job.isEmpty()) {
            return ResponseEntity.badRequest().body("Job not found");
        }

        // Check if applicant exists
        Optional<User> applicant = userService.getUserById(applicantId);
        if (applicant.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Check if already applied
        if (applicationService.hasUserApplied(jobId, applicantId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("You have already applied for this job");
        }

        // Create application
        Application application = new Application();
        application.setJob(job.get());
        application.setApplicant(applicant.get());
        application.setCoverLetter(coverLetter);
        application.setStatus(ApplicationStatus.PENDING);

        Application savedApplication = applicationService.saveApplication(application);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications(){
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id){
        Optional<Application> application = applicationService.getApplicationById(id);
        return application.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<List<Application>> getApplicationsByApplicant(@PathVariable Long applicantId) {
        List<Application> applications = applicationService.getApplicationsByApplicantId(applicantId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getApplicationsByJob(@PathVariable Long jobId) {
        List<Application> applications = applicationService.getApplicationsByJobId(jobId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Application>> getApplicationsByStatus(@PathVariable ApplicationStatus status) {
        List<Application> applications = applicationService.getApplicationsByStatus(status);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/job/{jobId}/status/{status}")
    public ResponseEntity<List<Application>> getApplicationsByJobAndStatus(
            @PathVariable Long jobId,
            @PathVariable ApplicationStatus status) {
        List<Application> applications = applicationService.getApplicationsByJobAndStatus(jobId, status);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/job/{jobId}/count")
    public ResponseEntity<Long> countApplicationsForJob(@PathVariable Long jobId) {
        long count = applicationService.countApplicationsForJob(jobId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> hasUserApplied(
            @RequestParam Long jobId,
            @RequestParam Long applicantId) {
        boolean hasApplied = applicationService.hasUserApplied(jobId, applicantId);
        return ResponseEntity.ok(hasApplied);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        Application updatedApplication = applicationService.updateApplicationStatus(id, status);
        if (updatedApplication != null) {
            return ResponseEntity.ok(updatedApplication);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/notes")
    public ResponseEntity<Application> addNotes(
            @PathVariable Long id,
            @RequestParam String notes) {
        Application updatedApplication = applicationService.addNotesToApplication(id, notes);
        if (updatedApplication != null) {
            return ResponseEntity.ok(updatedApplication);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        Optional<Application> application = applicationService.getApplicationById(id);
        if (application.isPresent()) {
            applicationService.deleteApplication(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
