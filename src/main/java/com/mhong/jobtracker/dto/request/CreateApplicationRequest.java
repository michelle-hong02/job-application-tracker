package com.mhong.jobtracker.dto.request;

import com.mhong.jobtracker.domain.ApplicationStatus;
import com.mhong.jobtracker.domain.JobApplication;
import com.mhong.jobtracker.domain.User;
import com.mhong.jobtracker.domain.WorkType;
import jakarta.persistence.*;

import java.time.LocalDate;

public class CreateApplicationRequest {

    private Long userId;
    private String company;
    private String role;
    private LocalDate applyDate;
    private ApplicationStatus status;
    private WorkType workType;
    private Double salaryMin;
    private Double salaryMax;
    private String notes;

    // No-args constructor
    public CreateApplicationRequest() {}

    // All-args constructor
    public CreateApplicationRequest(Long userId, String company, String role,
                                    LocalDate applyDate, ApplicationStatus status,
                                    WorkType workType, Double salaryMin,
                                    Double salaryMax, String notes) {
        this.userId = userId;
        this.company = company;
        this.role = role;
        this.applyDate = applyDate;
        this.status = status;
        this.workType = workType;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.notes = notes;
    }


    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDate getApplyDate() { return applyDate; }
    public void setApplyDate(LocalDate applyDate) { this.applyDate = applyDate; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public WorkType getWorkType() { return workType; }
    public void setWorkType(WorkType workType) { this.workType = workType; }

    public Double getSalaryMin() { return salaryMin; }
    public void setSalaryMin(Double salaryMin) { this.salaryMin = salaryMin; }

    public Double getSalaryMax() { return salaryMax; }
    public void setSalaryMax(Double salaryMax) { this.salaryMax = salaryMax; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // Method to convert DTO to JobApplication entity
    public JobApplication createApplication(User user) {
        JobApplication app = new JobApplication(user, this.company, this.role);

        if (this.applyDate != null) app.setApplyDate(this.applyDate);
        if (this.status != null) app.setStatus(this.status);
        if (this.workType != null) app.setWorkType(this.workType);
        if (this.salaryMin != null) app.setSalaryMin(this.salaryMin);
        if (this.salaryMax != null) app.setSalaryMax(this.salaryMax);
        if (this.notes != null) app.setNotes(this.notes);

        return app;
    }

}
