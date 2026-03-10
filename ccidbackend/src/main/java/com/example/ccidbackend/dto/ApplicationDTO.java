package com.example.ccidbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDTO {
    private Long id;
   
    private String submissionMethod;
    private java.time.LocalDate receiptDate;
    private java.time.LocalDate resultDate;
    private String competentAuth;
    private String certSignatory;
    private String signatoryTitle;
    private String status;  
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;
    private Long userId;
    private LegalizationStampDTO legalizationStamp;
    private String image_url;
}
