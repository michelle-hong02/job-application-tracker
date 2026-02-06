package com.mhong.jobtracker.service;

import com.mhong.jobtracker.exception.ApplicationNotFoundException;
import com.mhong.jobtracker.exception.UnauthorizedAccessException;
import com.mhong.jobtracker.exception.UserNotFoundException;
import com.mhong.jobtracker.domain.ApplicationStatus;
import com.mhong.jobtracker.domain.JobApplication;
import com.mhong.jobtracker.domain.User;
import com.mhong.jobtracker.domain.WorkType;
import com.mhong.jobtracker.dto.request.CreateApplicationRequest;
import com.mhong.jobtracker.dto.request.UpdateApplicationRequest;
import com.mhong.jobtracker.repository.JobApplicationRepository;
import com.mhong.jobtracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobApplicationService {

    // injecting repository
    private final JobApplicationRepository repo;
    private final UserRepository userRepository;

    public JobApplicationService(JobApplicationRepository repo, UserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
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

        JobApplication app = new JobApplication(user, request.getCompany(), request.getRole());

        setOptionalFields(app, request.getApplyDate(), request.getStatus(), request.getWorkType(), request.getSalaryMin(), request.getSalaryMax(), request.getNotes());

        repo.save(app);

        return app;

    }

    public JobApplication updateApplication(User user, UpdateApplicationRequest request) {

        // Fetch the application
        JobApplication app = repo.findById(request.getAppId())
                .orElseThrow(() -> new ApplicationNotFoundException(request.getAppId()));

        // Ownership check: only the owner can update
        if (!app.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You do not have permission to update this application");
        }

        // Updates fields if provided
        if (request.getCompany() != null) app.setCompany(request.getCompany());
        if (request.getRole() != null) app.setRole(request.getRole());
        setOptionalFields(app, request.getApplyDate(), request.getStatus(), request.getWorkType(), request.getSalaryMin(), request.getSalaryMax(), request.getNotes());

        return repo.save(app); // persist changes
    }

    public List<JobApplication> getAllApplications(User user) {
        return repo.findAllByUserId(user.getId());
    }

    public JobApplication getApplicationById(Long id){
       return repo.findById(id)
               .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    public void deleteApplication(User user, Long appId) {
        JobApplication app = repo.findById(appId)
                .orElseThrow(() -> new ApplicationNotFoundException(appId));

        if (!app.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You do not have permission to delete this application");
        }

        repo.delete(app);
    }
}
