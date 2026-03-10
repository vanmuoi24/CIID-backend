package com.example.ccidbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hình thức nộp hồ sơ
    @Column(name = "submission_method", nullable = true)
    private String submissionMethod;

    // Ngày nhận hồ sơ
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "receipt_date", nullable = true)
    private LocalDate receiptDate;

    // Cơ quan giải quyết
    @Column(name = "competent_authority", nullable = true)
    private String competentAuth;

    // Ngày trả kết quả
        @JsonFormat(pattern = "dd/MM/yyyy")

    @Column(name = "result_date", nullable = true)
    private LocalDate resultDate;

    // Người ký chứng nhận
    @Column(name = "certifying_signatory", nullable = true)
    private String certSignatory;

    // Chức danh người ký
    @Column(name = "signatory_title", nullable = true)
    private String signatoryTitle;

    // Trạng thái
    @Column(nullable = true)
    private String status;

    // Audit
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    @JoinColumn(name = "legalization_stamp_id")
    private LegalizationStamp legalizationStamp;

}