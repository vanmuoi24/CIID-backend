package com.example.ccidbackend.service;

import com.example.ccidbackend.dto.ApplicationDTO;
import com.example.ccidbackend.dto.LegalizationStampDTO;
import com.example.ccidbackend.entity.Application;
import com.example.ccidbackend.entity.Document;
import com.example.ccidbackend.entity.LegalizationStamp;
import com.example.ccidbackend.repository.ApplicationRepository;
import com.example.ccidbackend.repository.LegalizationStampRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final LegalizationStampService legalizationStampService;
    private final LegalizationStampRepository legalizationStampRepository;

    // ================================
    // CREATE (Save full 1-1-1)
    // ================================
    public ApplicationDTO createApplication(Application application, Long stampId) {

        if (application.getStatus() == null) {
            application.setStatus("PENDING");
        }

        // LegalizationStamp stamp = application.getLegalizationStamp();
        LegalizationStamp stamp = legalizationStampRepository.findById(stampId)
                .orElseThrow(() -> new RuntimeException("Stamp not found with id: " + stampId));

        if (stamp != null) {

            // set chiều ngược lại
            stamp.setApplication(application);
            // sửa chỗ này để set cho application
            application.setLegalizationStamp(stamp);
            Document document = stamp.getDocument();
            if (document != null) {
                document.setLegalizationStamp(stamp);
            }
        }

        Application saved = applicationRepository.save(application);
        return convertToDTO(saved);
    }

    // ================================
    // GET BY ID
    // ================================
    @Transactional(readOnly = true)
    public ApplicationDTO getApplicationById(Long id) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        return convertToDTO(app);
    }

    // ================================
    // GET ALL
    // ================================
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getAllApplications() {
        return applicationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ================================
    // GET BY STATUS
    // ================================
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getApplicationsByStatus(String status) {
        return applicationRepository.findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ================================
    // UPDATE
    // ================================
    public ApplicationDTO updateApplication(Long id, Application request) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        if (request.getSubmissionMethod() != null)
            app.setSubmissionMethod(request.getSubmissionMethod());

        if (request.getReceiptDate() != null)
            app.setReceiptDate(request.getReceiptDate());

        if (request.getResultDate() != null)
            app.setResultDate(request.getResultDate());

        if (request.getCompetentAuth() != null)
            app.setCompetentAuth(request.getCompetentAuth());

        if (request.getCertSignatory() != null)
            app.setCertSignatory(request.getCertSignatory());

        if (request.getSignatoryTitle() != null)
            app.setSignatoryTitle(request.getSignatoryTitle());

        if (request.getStatus() != null)
            app.setStatus(request.getStatus());

        Application updated = applicationRepository.save(app);
        return convertToDTO(updated);
    }

    // ================================
    // DELETE
    // ================================
    public void deleteApplication(Long id) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        applicationRepository.delete(app);
    }

    // ================================
    // CONVERT TO DTO
    // ================================
    private ApplicationDTO convertToDTO(Application app) {

        LegalizationStampDTO stampDTO = null;

        if (app.getLegalizationStamp() != null) {
            stampDTO = legalizationStampService
                    .convertToDTO(app.getLegalizationStamp());
        }

        return ApplicationDTO.builder()
                .id(app.getId())
                .submissionMethod(app.getSubmissionMethod())
                .receiptDate(app.getReceiptDate())
                .resultDate(app.getResultDate())
                .competentAuth(app.getCompetentAuth())
                .certSignatory(app.getCertSignatory())
                .signatoryTitle(app.getSignatoryTitle())
                .status(app.getStatus())
                .createdAt(app.getCreatedAt())
                .updatedAt(app.getUpdatedAt())
                .legalizationStamp(stampDTO)
                .build();
    }
}