package com.mhong.jobtracker.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class JobApplication {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false) // Many applications can belong to one user
    @JoinColumn(name = "user_id")  // Column in the JobApplication table
    private User user;

    private String company;
    private String role;
    private LocalDate applyDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Enumerated(EnumType.STRING)
    private WorkType workType;

    private Double salaryMin;
    private Double salaryMax;
    private String notes;

    protected JobApplication(){}

    public JobApplication(User user, String company, String role){
        this.user = user;
        this.company = company;
        this.role = role;
        this.applyDate = LocalDate.now();
        this.status = ApplicationStatus.APPLIED;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getCompany() {
        return company;
    }

    public String getRole() {
        return role;
    }

    public LocalDate getApplyDate() {
        return applyDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public Double getSalaryMin() {
        return salaryMin;
    }

    public Double getSalaryMax() {
        return salaryMax;
    }

    public String getNotes() {
        return notes;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void setSalaryMin(Double salaryMin) {
        this.salaryMin = salaryMin;
    }

    public void setSalaryMax(Double salaryMax) {
        this.salaryMax = salaryMax;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
