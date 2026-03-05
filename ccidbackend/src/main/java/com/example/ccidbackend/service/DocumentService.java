package com.example.ccidbackend.service;

import com.example.ccidbackend.dto.DocumentDTO;
import com.example.ccidbackend.entity.Document;
import com.example.ccidbackend.entity.LegalizationStamp;
import com.example.ccidbackend.repository.DocumentRepository;
import com.example.ccidbackend.repository.LegalizationStampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final LegalizationStampRepository stampRepository;

    // =====================================
    // CREATE DOCUMENT (attach vào Stamp)
    // =====================================
    public DocumentDTO createDocument(Document document, Long stampId) {

        LegalizationStamp stamp = stampRepository.findById(stampId)
                .orElseThrow(() -> new RuntimeException("Stamp not found with id: " + stampId));

        // Set 2 chiều
        document.setLegalizationStamp(stamp);
        stamp.setDocument(document);

        Document saved = documentRepository.save(document);
        return convertToDTO(saved);
    }

    // =====================================
    // GET BY ID
    // =====================================
    @Transactional(readOnly = true)
    public DocumentDTO getDocumentById(Long id) {
        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
        return convertToDTO(doc);
    }

    // =====================================
    // GET ALL
    // =====================================
    @Transactional(readOnly = true)
    public List<DocumentDTO> getAllDocuments() {
        return documentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // =====================================
    // UPDATE
    // =====================================
    public DocumentDTO updateDocument(Long id, Document request) {

        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));

        if (request.getCvType() != null)
            doc.setCvType(request.getCvType());

        if (request.getDocumentTitle() != null)
            doc.setDocumentTitle(request.getDocumentTitle());

        if (request.getDocumentType() != null)
            doc.setDocumentType(request.getDocumentType());

        if (request.getHolderName() != null)
            doc.setHolderName(request.getHolderName());

        if (request.getReferenceNumber() != null)
            doc.setReferenceNumber(request.getReferenceNumber());

        if (request.getIssueDate() != null)
            doc.setIssueDate(request.getIssueDate());

        if (request.getCertifyingAuthority() != null)
            doc.setCertifyingAuthority(request.getCertifyingAuthority());

        if (request.getCertifyingSignatory() != null)
            doc.setCertifyingSignatory(request.getCertifyingSignatory());

        if (request.getCertifyingTitle() != null)
            doc.setCertifyingTitle(request.getCertifyingTitle());

        return convertToDTO(doc);
    }

    // =====================================
    // DELETE
    // =====================================
    public void deleteDocument(Long id) {
        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
        documentRepository.delete(doc);
    }

    // =====================================
    // CONVERT TO DTO
    // =====================================
    public DocumentDTO convertToDTO(Document doc) {
        return DocumentDTO.builder()
                .id(doc.getId())
                .cvType(doc.getCvType())
                .documentTitle(doc.getDocumentTitle())
                .documentType(doc.getDocumentType())
                .holderName(doc.getHolderName())
                .referenceNumber(doc.getReferenceNumber())
                .issueDate(doc.getIssueDate())
                .certifyingAuthority(doc.getCertifyingAuthority())
                .certifyingSignatory(doc.getCertifyingSignatory())
                .certifyingTitle(doc.getCertifyingTitle())
                .build();
    }
}