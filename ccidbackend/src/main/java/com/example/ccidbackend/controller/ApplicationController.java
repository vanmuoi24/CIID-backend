package com.example.ccidbackend.controller;

import com.example.ccidbackend.dto.ApiResponse;
import com.example.ccidbackend.dto.ApplicationDTO;
import com.example.ccidbackend.entity.Application;
import com.example.ccidbackend.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApiResponse<ApplicationDTO>> createApplication(
            @RequestBody ApplicationDTO applicationDTO) {

                System.out.println("application DTO" + applicationDTO);
        try {
            Application application = new Application();
        
            application.setSubmissionMethod(applicationDTO.getSubmissionMethod());
            application.setReceiptDate(applicationDTO.getReceiptDate());
            application.setCompetentAuth(applicationDTO.getCompetentAuth());
            application.setResultDate(applicationDTO.getResultDate());
            application.setCertSignatory(applicationDTO.getCertSignatory());
            application.setSignatoryTitle(applicationDTO.getSignatoryTitle());
            application.setStatus(applicationDTO.getStatus());
            
            ApplicationDTO appDTO = applicationService.createApplication(application, applicationDTO.getLegalizationStamp().getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(appDTO, "Application created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to create application", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplicationDTO>> getApplicationById(@PathVariable Long id) {
        try {
            ApplicationDTO appDTO = applicationService.getApplicationById(id);
            return ResponseEntity.ok(ApiResponse.success(appDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Application not found", e.getMessage()));
        }
    }

   
  

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<ApplicationDTO>>> getApplicationsByStatus(@PathVariable String status) {
        try {
            List<ApplicationDTO> apps = applicationService.getApplicationsByStatus(status);
            return ResponseEntity.ok(ApiResponse.success(apps));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch applications", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApplicationDTO>>> getAllApplications() {
        try {
            List<ApplicationDTO> apps = applicationService.getAllApplications();
            return ResponseEntity.ok(ApiResponse.success(apps));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch applications", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplicationDTO>> updateApplication(
            @PathVariable Long id,
            @RequestBody ApplicationDTO applicationDTO) {
        try {
            Application application = new Application();

            application.setSubmissionMethod(applicationDTO.getSubmissionMethod());
            application.setReceiptDate(applicationDTO.getReceiptDate());
            application.setCompetentAuth(applicationDTO.getCompetentAuth());
            application.setResultDate(applicationDTO.getResultDate());
            application.setCertSignatory(applicationDTO.getCertSignatory());
            application.setSignatoryTitle(applicationDTO.getSignatoryTitle());
            application.setStatus(applicationDTO.getStatus());
            
            ApplicationDTO appDTO = applicationService.updateApplication(id, application);
            return ResponseEntity.ok(ApiResponse.success(appDTO, "Application updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Application not found", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteApplication(@PathVariable Long id) {
        try {
            applicationService.deleteApplication(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Application deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Application not found", e.getMessage()));
        }
    }
}
