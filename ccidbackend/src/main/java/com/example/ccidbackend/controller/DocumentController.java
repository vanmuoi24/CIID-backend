package com.example.ccidbackend.controller;

import com.example.ccidbackend.dto.ApiResponse;
import com.example.ccidbackend.dto.DocumentDTO;
import com.example.ccidbackend.entity.Document;
import com.example.ccidbackend.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<ApiResponse<DocumentDTO>> createDocument(
            @RequestBody DocumentDTO documentDTO) {
        try {
            Document document = new Document();
            document.setCvType(documentDTO.getCvType());
            document.setDocumentTitle(documentDTO.getDocumentTitle());
            document.setDocumentType(documentDTO.getDocumentType());
            document.setHolderName(documentDTO.getHolderName());
            document.setReferenceNumber(documentDTO.getReferenceNumber());
            document.setIssueDate(documentDTO.getIssueDate());
            document.setCertifyingAuthority(documentDTO.getCertifyingAuthority());
            document.setCertifyingSignatory(documentDTO.getCertifyingSignatory());
            document.setCertifyingTitle(documentDTO.getCertifyingTitle());
            
            DocumentDTO docDTO = documentService.createDocument(document, documentDTO.getApplicationId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(docDTO, "Document created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to create document", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentDTO>> getDocumentById(@PathVariable Long id) {
        try {
            DocumentDTO docDTO = documentService.getDocumentById(id);
            return ResponseEntity.ok(ApiResponse.success(docDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Document not found", e.getMessage()));
        }
    }

   

    @GetMapping
    public ResponseEntity<ApiResponse<List<DocumentDTO>>> getAllDocuments() {
        try {
            List<DocumentDTO> docs = documentService.getAllDocuments();
            return ResponseEntity.ok(ApiResponse.success(docs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch documents", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentDTO>> updateDocument(
            @PathVariable Long id,
            @RequestBody DocumentDTO documentDTO) {
        try {
            Document document = new Document();
            document.setCvType(documentDTO.getCvType());
            document.setDocumentTitle(documentDTO.getDocumentTitle());
            document.setDocumentType(documentDTO.getDocumentType());
            document.setHolderName(documentDTO.getHolderName());
            document.setReferenceNumber(documentDTO.getReferenceNumber());
            document.setIssueDate(documentDTO.getIssueDate());
            document.setCertifyingAuthority(documentDTO.getCertifyingAuthority());
            document.setCertifyingSignatory(documentDTO.getCertifyingSignatory());
            document.setCertifyingTitle(documentDTO.getCertifyingTitle());
            
            DocumentDTO docDTO = documentService.updateDocument(id, document);
            return ResponseEntity.ok(ApiResponse.success(docDTO, "Document updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Document not found", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDocument(@PathVariable Long id) {
        try {
            documentService.deleteDocument(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Document deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Document not found", e.getMessage()));
        }
    }
}
