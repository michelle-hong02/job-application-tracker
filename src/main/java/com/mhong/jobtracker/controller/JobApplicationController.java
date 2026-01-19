package com.mhong.jobtracker.controller;

import com.mhong.jobtracker.domain.JobApplication;
import com.mhong.jobtracker.dto.request.CreateApplicationRequest;
import com.mhong.jobtracker.dto.request.UpdateApplicationRequest;
import com.mhong.jobtracker.dto.response.ApplicationResponse;
import com.mhong.jobtracker.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/applications")
public class JobApplicationController {

    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    // Create job application
    @PostMapping
    public ResponseEntity<ApplicationResponse> create(@Valid  @RequestBody CreateApplicationRequest request)
    { JobApplication app = service.createApplication(request);
        return ResponseEntity
                .created(URI.create("/applications/" + app.getId())) // 201 Created
                .body(ApplicationResponse.fromEntity(app));
    }

    // Get job application by appId
    @GetMapping("/{id}")
    public ApplicationResponse get(@PathVariable Long id){
        return ApplicationResponse.fromEntity(service.getApplicationById(id));
    }

    // Update existing job application
    @PatchMapping("/{id}")
    public ApplicationResponse update(@PathVariable Long id, @RequestBody UpdateApplicationRequest request) {
        request.setAppId(id);
        return ApplicationResponse.fromEntity(service.updateApplication(request));
    }


    // Delete existing job application by appId
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteApplication(id);
        return ResponseEntity.noContent().build(); // 204 No content
    }

    // Get all applications by userId
    @GetMapping("/user/{userId}")
    public List<ApplicationResponse> getAllByUser(@PathVariable Long userId){
        return service.getAllApplications(userId).stream()
                .map(ApplicationResponse::fromEntity)
                .toList();
    }
}
