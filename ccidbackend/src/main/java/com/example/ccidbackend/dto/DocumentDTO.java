package com.example.ccidbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DocumentDTO {

    private Long id;
    private Long applicationId;

    private String cvType;
    private String documentTitle;
    private String documentType;
    private String holderName;
    private String referenceNumber;
    private LocalDate issueDate;

    private String certifyingAuthority;
    private String certifyingSignatory;
    private String certifyingTitle;
}