package com.jobportal.controller;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.enums.JobStatus;
import com.jobportal.enums.JobType;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    @Autowired
    private final JobService jobService;

    @Autowired
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job, @RequestParam Long employerId) {
        Optional<User> employer = userService.getUserById(employerId);
        if (employer.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        job.setEmployer(employer.get());
        Job savedJob = jobService.saveJob(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Optional<Job> job = jobService.getJobById(id);
        return job.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/open")
    public ResponseEntity<List<Job>> getAllOpenJobs() {
        List<Job> jobs = jobService.getAllOpenJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/employer/{employerId}")
    public ResponseEntity<List<Job>> getJobsByEmployer(@PathVariable Long employerId) {
        List<Job> jobs = jobService.getJobByEmployerId(employerId);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Job>> getJobByStatus(@PathVariable JobStatus status) {
        List<Job> jobs = jobService.getJobByStatus(status);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Job>> getJobByType(@PathVariable JobType type){
        List<Job> jobs = jobService.getJobByType(type);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Job>> getJobByTitle(@PathVariable String title){
        List<Job> jobs = jobService.getJobByTitle(title);
        return ResponseEntity.ok(jobs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job){
        Job updatedJob = jobService.updateJob(id,job);
        if(updatedJob != null){
            return ResponseEntity.ok(updatedJob);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<Job> closeJob(@PathVariable Long id){
        Job closedJob = jobService.closeJob(id);
        if(closedJob != null){
            return ResponseEntity.ok(closedJob);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id){
        Optional<Job> job = jobService.getJobById(id);
        if(job.isPresent()){
            jobService.deleteJob(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
