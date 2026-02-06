package com.mhong.jobtracker.controller;

import com.mhong.jobtracker.domain.JobApplication;
import com.mhong.jobtracker.domain.User;
import com.mhong.jobtracker.dto.request.CreateApplicationRequest;
import com.mhong.jobtracker.dto.request.UpdateApplicationRequest;
import com.mhong.jobtracker.dto.response.ApplicationResponse;
import com.mhong.jobtracker.service.JobApplicationService;
import com.mhong.jobtracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/applications")
public class JobApplicationController {

    private final JobApplicationService service;
    private final UserService userService;

    public JobApplicationController(JobApplicationService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    // Create job application
    @PostMapping
    public ResponseEntity<ApplicationResponse> create(
            @Valid  @RequestBody CreateApplicationRequest request,
            Authentication authentication
    )
    {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        JobApplication app = service.createApplication(user, request);
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
    public ApplicationResponse update(@PathVariable Long id,
                                      @RequestBody UpdateApplicationRequest request,
                                      Authentication authentication) {

        User user = userService.getUserByUsername(authentication.getName());
        request.setAppId(id);
        return ApplicationResponse.fromEntity(service.updateApplication(user, request));
    }


    // Delete existing job application by appId
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication){

        User user = userService.getUserByUsername(authentication.getName());
        service.deleteApplication(user, id);
        return ResponseEntity.noContent().build(); // 204 No content
    }

    // Get all applications by userId
    @GetMapping("/me")
    public List<ApplicationResponse> getAllForCurrentUser(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        return service.getAllApplications(user).stream()
                .map(ApplicationResponse::fromEntity)
                .toList();
    }

}
