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
    private LocalDate applyDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Enumerated(EnumType.STRING)
    private WorkType workType;

    private Double salaryMin;
    private Double salaryMax;
    private String notes;

    protected JobApplication(){}

    public JobApplication(User user, String company){
        this.user = user;
        this.company = company;
        this.applyDate = LocalDate.now();
        this.status = ApplicationStatus.APPLIED;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
