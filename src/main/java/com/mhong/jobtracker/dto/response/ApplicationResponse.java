package com.mhong.jobtracker.dto.response;

import com.mhong.jobtracker.domain.ApplicationStatus;
import com.mhong.jobtracker.domain.JobApplication;
import com.mhong.jobtracker.domain.WorkType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ApplicationResponse {

    private final Long appId;
    private final String company;
    private final String role;
    private final LocalDate applyDate;
    private final ApplicationStatus status;
    private final WorkType workType;
    private final Double salaryMin;
    private final Double salaryMax;
    private final String notes;

    // All-args constructor
    public ApplicationResponse(Long appId, String company, String role,
                               LocalDate applyDate, ApplicationStatus status,
                               WorkType workType, Double salaryMin,
                               Double salaryMax, String notes) {
        this.appId = appId;
        this.company = company;
        this.role = role;
        this.applyDate = applyDate;
        this.status = status;
        this.workType = workType;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.notes = notes;
    }

    // Static factory to convert from entity
    public static ApplicationResponse fromEntity(JobApplication app) {
        return new ApplicationResponse(
                app.getId(),
                app.getCompany(),
                app.getRole(),
                app.getApplyDate(),
                app.getStatus(),
                app.getWorkType(),
                app.getSalaryMin(),
                app.getSalaryMax(),
                app.getNotes()
        );
    }

    // Getters only
    // Note: You need getters to serialize the object to JSON ins repsonses
    public Long getAppId() { return appId; }
    public String getCompany() { return company; }
    public String getRole() { return role; }
    public LocalDate getApplyDate() { return applyDate; }
    public ApplicationStatus getStatus() { return status; }
    public WorkType getWorkType() { return workType; }
    public Double getSalaryMin() { return salaryMin; }
    public Double getSalaryMax() { return salaryMax; }
    public String getNotes() { return notes; }
}
