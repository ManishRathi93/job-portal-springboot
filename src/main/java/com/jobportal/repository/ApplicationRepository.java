package com.jobportal.repository;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {

    List<Application> findByApplicant(User applicant);

//    List<Application> findAllApplication(Long applicantId);

    List<Application> findByJob(Job job);

    List<Application> findByJobId(Long jobId);

    List<Application> findByStatus(ApplicationStatus status);

//    List<Application> findByJobAndStatus(Long jobId, ApplicationStatus status);

    List<Application> findByApplicantIdAndStatus(Long applicantId, ApplicationStatus status);

    boolean existsByJobIdAndApplicantId(Long jobId, Long applicationId);

    Optional<Application> findByJobIdAndApplicantId(Long jobId, Long applicantId);

    Long countByJobId(Long jobId);

    List<Application> findByApplicantId(Long applicantId);

    List<Application> findByJobIdAndStatus(Long jobId, ApplicationStatus status);
}
