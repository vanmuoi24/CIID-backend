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
public class LegalizationStampDTO {
    private Long id;
    private String country;
    private String signedBy;
    private String signedTitle;
    private String notaryOffice;
    private String certifiedPlace;
    private LocalDate certifiedDate;
    private String consularDepartment;
    private String stampNumber;
    private String qrCode;
    private String signatureImage;
    private String image_url;
    private ApplicationDTO application; // Thêm trường này nếu muốn trả về thông tin application liên quan
    private DocumentDTO document; // Thêm trường này nếu muốn trả về thông tin document liên quan
}
