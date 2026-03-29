package com.mhong.jobtracker.service;

import com.mhong.jobtracker.exception.ApplicationNotFoundException;
import com.mhong.jobtracker.exception.UnauthorizedAccessException;
import com.mhong.jobtracker.domain.ApplicationStatus;
import com.mhong.jobtracker.domain.JobApplication;
import com.mhong.jobtracker.domain.User;
import com.mhong.jobtracker.domain.WorkType;
import com.mhong.jobtracker.dto.request.CreateApplicationRequest;
import com.mhong.jobtracker.dto.request.UpdateApplicationRequest;
import com.mhong.jobtracker.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JobApplicationService {

    // injecting repository
    private final JobApplicationRepository repo;
    private static final Logger log = LoggerFactory.getLogger(JobApplicationService.class);

    public JobApplicationService(JobApplicationRepository repo) {
        this.repo = repo;
    }

    private void setOptionalFields(JobApplication app, LocalDate applyDate, ApplicationStatus status, WorkType workType, Double salaryMin, Double salaryMax, String notes) {
        if (applyDate != null) app.setApplyDate(applyDate);
        if (status != null) app.setStatus(status);
        if (workType != null) app.setWorkType(workType);
        if (salaryMin != null) app.setSalaryMin(salaryMin);
        if (salaryMax != null) app.setSalaryMax(salaryMax);
        if (notes != null) app.setNotes(notes);
    }

    public JobApplication createApplication(User user, CreateApplicationRequest request) {

        log.info("Creating application for userId={}", user.getId());
        JobApplication app = new JobApplication(user, request.getCompany(), request.getRole());

        setOptionalFields(app, request.getApplyDate(), request.getStatus(), request.getWorkType(), request.getSalaryMin(), request.getSalaryMax(), request.getNotes());

        repo.save(app);
        log.info("Successfully created application id={} userId={}", app.getId(), user.getId());

        return app;
    }

    public JobApplication updateApplication(User user, UpdateApplicationRequest request) {

        // Fetch the application
        log.info("Fetching application id={} for update userId={}", request.getAppId(), user.getId());
        JobApplication app = repo.findById(request.getAppId())
                .orElseThrow(() -> new ApplicationNotFoundException(request.getAppId()));
        log.info("Successfully fetched application id={} userId={}", app.getId(), user.getId());

        // Ownership check: only the owner can update
        log.info("Running ownership check");
        if (!app.getUser().getId().equals(user.getId())) {
            log.warn("You do not have permission to update this application");
            throw new UnauthorizedAccessException("You do not have permission to update this application");
        }
        log.info("Ownership check successful, updating application");

        // Updates fields if provided
        if (request.getCompany() != null) app.setCompany(request.getCompany());
        if (request.getRole() != null) app.setRole(request.getRole());
        setOptionalFields(app, request.getApplyDate(), request.getStatus(), request.getWorkType(), request.getSalaryMin(), request.getSalaryMax(), request.getNotes());

        app = repo.save(app);
        log.info("Successfully updated application id={} userId={}", app.getId(), user.getId());

        return app;
    }

    public List<JobApplication> getAllApplications(User user) {

        log.info("Fetching all applications for userId={}", user.getId());
        List<JobApplication> apps = repo.findAllByUserId(user.getId());
        log.info("Fetched {} applications for userId={}", apps.size(), user.getId());

        return apps;
    }

    public JobApplication getApplicationById(User user, Long id){

        log.info("Fetching application id={} for userId={}", id, user.getId());

        // Fetch the application
        JobApplication app = repo.findById(id)
               .orElseThrow(() -> new ApplicationNotFoundException(id));

        log.info("Successfully fetched application id={} userId={}", app.getId(), user.getId());

        // Ownership check: only the owner can access app
        log.info("Running ownership check");
        if (!app.getUser().getId().equals(user.getId())) {
            log.warn("User {} tried to update application {} without permission", user.getId(), app.getId());
            throw new UnauthorizedAccessException("You do not have permission to view this application");
        }
        log.info("Ownership check passed for userId={} applicationId={}", user.getId(), app.getId());
        return app;
    }

    public void deleteApplication(User user, Long appId) {

        log.info("Fetching application id={} for deletion userId={}", appId, user.getId());
        JobApplication app = repo.findById(appId)
                .orElseThrow(() -> new ApplicationNotFoundException(appId));

        log.info("Deleting application id={} userId={}", app.getId(), user.getId());
        // Ownership check: only the owner can delete the app
        if (!app.getUser().getId().equals(user.getId())) {
            log.warn("User {} tried to delete application {} without permission", user.getId(), appId);
            throw new UnauthorizedAccessException("You do not have permission to delete this application");
        }
        log.info("Ownership check passed for userId={} applicationId={}", user.getId(), appId);

        repo.delete(app);
        log.info("Successfully deleted application id={} userId={}", app.getId(), user.getId());
    }
}
