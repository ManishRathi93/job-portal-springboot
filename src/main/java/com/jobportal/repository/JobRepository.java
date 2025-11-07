package com.jobportal.repository;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.enums.JobStatus;
import com.jobportal.enums.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

    List<Job> findByEmployer(User employer);

    List<Job> findByEmployerId(Long employerId);

    List<Job> findByStatus(JobStatus status);

    List<Job> findByJobType(JobType jobType);

    List<Job> findByLocationContainingIgnoreCase(String location);

    List<Job> findByTitleContainingIgnoreCase(String title);

    List<Job> findByStatusAndEmployerId(JobStatus status, Long employerId);

    List<Job> findByStatusOrderByCreatedAtDesc(JobStatus status);
}
