package com.mhong.jobtracker.dto.request;

import com.mhong.jobtracker.domain.ApplicationStatus;
import com.mhong.jobtracker.domain.WorkType;

import java.time.LocalDate;

public class UpdateApplicationRequest {

    private Long appId; //required
    private String company;
    private String role;
    private LocalDate applyDate;
    private ApplicationStatus status;
    private WorkType workType;
    private Double salaryMin;
    private Double salaryMax;
    private String notes;

    // No-args constructor
    public UpdateApplicationRequest() {}

    // All-args constructor
    public UpdateApplicationRequest(Long appId, String company, String role,
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


    // Getters and setters
    public Long getAppId() { return appId; }
    public void setAppId(Long appId) { this.appId = appId; }

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

}
