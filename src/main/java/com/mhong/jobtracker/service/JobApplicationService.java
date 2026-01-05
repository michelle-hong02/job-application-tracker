package com.mhong.jobtracker.service;

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
import java.util.Optional;

@Service
public class JobApplicationService {

    // injecting repository
    private final JobApplicationRepository repo;

    public JobApplicationService(JobApplicationRepository repo) {
        this.repo = repo;
    }

    private void setOptionalFields(JobApplication app, LocalDate applyDate, ApplicationStatus status, WorkType workType, Double salaryMin, Double salaryMax, String notes, CreateApplicationRequest request) {
        if (applyDate != null) app.setApplyDate(applyDate);
        if (status != null) app.setStatus(status);
        if (workType != null) app.setWorkType(workType);
        if (salaryMin != null) app.setSalaryMin(salaryMin);
        if (salaryMax != null) app.setSalaryMax(salaryMax);
        if (notes != null) app.setNotes(notes);
    }

    public JobApplication createApplication(CreateApplicationRequest request) {

        //Use UserService or repo to get the associated user id??
        User user = UserService.getUserId();

        JobApplication app = new JobApplication(user, request.getCompany(), request.getRole());

        setOptionalFields(app, request.getApplyDate(), request.getStatus(), request.getWorkType(), request.getSalaryMin(), request.getSalaryMax(), request.getNotes(), request);

        return app;
    }

    public JobApplication updateApplication(UpdateApplicationRequest request) {
        JobApplication app = repo.findById(request.getAppId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (request.getCompany() != null) app.setCompany(request.getCompany());
        if (request.getRole() != null) app.setRole(request.getRole());
        setOptionalFields(app, request.getApplyDate(), request.getStatus(), request.getWorkType(), request.getSalaryMin(), request.getSalaryMax(), request.getNotes(), request);

        return repo.save(app); // persist changes
    }

    public List<JobApplication> getAllApplications(Long userId) {
        return repo.findByUserId(userId);
    }

    public Optional<JobApplication> getApplicationById(Long id){
        return repo.findById(id);
    }

    public void deleteApplication(Long id){
        repo.deleteById(id);
    }
}
