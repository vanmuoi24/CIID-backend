package com.example.ccidbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "legalization_stamps")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalizationStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Có thể null nếu chưa gán document
  

    private String country;

    @Column(name = "signed_by")
    private String signedBy;

    @Column(name = "signed_title")
    private String signedTitle;

    @Column(name = "notary_office")
    private String notaryOffice;

    @Column(name = "certified_place")
    private String certifiedPlace;

    @Column(name = "certified_date")
    private LocalDate certifiedDate;

    @Column(name = "consular_department")
    private String consularDepartment;

    @Column(name = "stamp_number", unique = true)
    private String stampNumber;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "signature_image")
    private String signatureImage;

@OneToOne(mappedBy = "legalizationStamp", fetch = FetchType.LAZY)
private Application application;

   @OneToOne(mappedBy = "legalizationStamp", fetch = FetchType.LAZY)
private Document document;
}