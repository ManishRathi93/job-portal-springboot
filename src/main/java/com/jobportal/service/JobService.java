package com.jobportal.service;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.enums.JobStatus;
import com.jobportal.enums.JobType;
import com.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobService {

    @Autowired
    private final JobRepository jobRepository;


    public Job saveJob(Job job){
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }

    public Optional<Job> getJobById(Long id){
        return jobRepository.findById(id);
    }

    public List<Job> getJobByEmployer(User employer){
        return jobRepository.findByEmployer(employer);
    }

    public List<Job> findByEmployerId(Long id){
        return jobRepository.findByEmployerId(id);
    }

    public List<Job> getAllOpenJobs(){
        return jobRepository.findByStatusOrderByCreatedAtDesc(JobStatus.OPEN);
    }

    public List<Job> getJobByStatus(JobStatus status){
        return jobRepository.findByStatus(status);
    }

    public List<Job> getJobByType(JobType jobType){
        return jobRepository.findByJobType(jobType);
    }

    public List<Job> getJobByTitle(String title){
        return jobRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Job> searchJobsByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Job> getJobsByStatusAndEmployer(JobStatus status, Long employerId) {
        return jobRepository.findByStatusAndEmployerId(status, employerId);
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    public Job updateJob(Long id, Job updatedJob) {
        Optional<Job> existingJob = jobRepository.findById(id);
        if (existingJob.isPresent()) {
            Job job = existingJob.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setRequirements(updatedJob.getRequirements());
            job.setLocation(updatedJob.getLocation());
            job.setSalary(updatedJob.getSalary());
            job.setJobType(updatedJob.getJobType());
            job.setStatus(updatedJob.getStatus());
            job.setExperience(updatedJob.getExperience());
            job.setSkills(updatedJob.getSkills());
            job.setOpenings(updatedJob.getOpenings());
            return jobRepository.save(job);
        }
        return null;
    }

    public Job closeJob(Long id){
        Optional<Job> existingJob = jobRepository.findById(id);
        if(existingJob.isPresent()){
            Job job = existingJob.get();
            job.setStatus(JobStatus.CLOSED);
            return jobRepository.save(job);
        }
        return null;
    }

}
