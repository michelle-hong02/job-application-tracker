package com.mhong.jobtracker.service;

import com.mhong.jobtracker.exception.ApplicationNotFoundException;
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

    public JobApplication createApplication(CreateApplicationRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        JobApplication app = new JobApplication(user, request.getCompany(), request.getRole());

        setOptionalFields(app, request.getApplyDate(), request.getStatus(), request.getWorkType(), request.getSalaryMin(), request.getSalaryMax(), request.getNotes());

        repo.save(app);

        return app;

    }

    public JobApplication updateApplication(UpdateApplicationRequest request) {
        JobApplication app = repo.findById(request.getAppId())
                .orElseThrow(() -> new ApplicationNotFoundException(request.getAppId()));

        if (request.getCompany() != null) app.setCompany(request.getCompany());
        if (request.getRole() != null) app.setRole(request.getRole());
        setOptionalFields(app, request.getApplyDate(), request.getStatus(), request.getWorkType(), request.getSalaryMin(), request.getSalaryMax(), request.getNotes());

        return repo.save(app); // persist changes
    }

    public List<JobApplication> getAllApplications(Long userId) {
        return repo.findAllByUserId(userId);
    }

    public JobApplication getApplicationById(Long id){
       return repo.findById(id)
               .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    public void deleteApplication(Long id){

        repo.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
        repo.deleteById(id);

    }
}
