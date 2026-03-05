package com.example.ccidbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  

    // ===== 1. Loại CV =====
    @Column(name = "cv_type", nullable = false)
    private String cvType; // CNLS

    // ===== 2. Thông tin giấy tờ =====
    @Column(name = "document_title", nullable = false)
    private String documentTitle; // Bằng cao đẳng

    @Column(name = "document_type", nullable = false)
    private String documentType; // Bản dịch

    @Column(name = "holder_name", nullable = false)
    private String holderName; // TRẦN PHÚC THỊNH

    @Column(name = "reference_number", nullable = false)
    private String referenceNumber; // 15364

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate; // 05/02/2026

    // ===== 3. Thông tin cơ quan sao/chứng thực =====
    @Column(name = "certifying_authority", nullable = false)
    private String certifyingAuthority; 
    // VPCC Nguyễn Huệ, P. Ô Chợ Dừa, TP. Hà Nội

    @Column(name = "certifying_signatory", nullable = false)
    private String certifyingSignatory; 
    // Lê Như Tuân

    @Column(name = "certifying_title", nullable = false)
    private String certifyingTitle; 
    // Công chứng viên

    @OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "legalization_stamp_id", unique = true)
private LegalizationStamp legalizationStamp;
}