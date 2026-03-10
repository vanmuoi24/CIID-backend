package com.example.ccidbackend.controller;

import com.example.ccidbackend.dto.ApiResponse;
import com.example.ccidbackend.dto.LegalizationStampDTO;
import com.example.ccidbackend.entity.LegalizationStamp;
import com.example.ccidbackend.service.LegalizationStampService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/legalization-stamps")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class LegalizationStampController {

    private final LegalizationStampService legalizationStampService;
    private final com.example.ccidbackend.repository.LegalizationStampRepository legalizationStampRepository;
    private final com.example.ccidbackend.service.QRCodeService qrCodeService;

    @PostMapping
    public ResponseEntity<ApiResponse<LegalizationStampDTO>> createLegalizationStamp(
            @RequestBody LegalizationStamp stamp) {
        try {
            LegalizationStampDTO stampDTO = legalizationStampService.createLegalizationStamp(stamp);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(stampDTO, "Legalization stamp created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to create legalization stamp", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LegalizationStampDTO>> getLegalizationStampById(@PathVariable Long id) {
        try {
            LegalizationStampDTO stampDTO = legalizationStampService.getLegalizationStampById(id);
            return ResponseEntity.ok(ApiResponse.success(stampDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Legalization stamp not found", e.getMessage()));
        }
    }

    @GetMapping("/stamp-number/{stampNumber}")
    public ResponseEntity<ApiResponse<LegalizationStampDTO>> getLegalizationStampByStampNumber(
            @PathVariable String stampNumber) {
        try {
            LegalizationStampDTO stampDTO = legalizationStampService.getLegalizationStampByStampNumber(stampNumber);
            return ResponseEntity.ok(ApiResponse.success(stampDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Legalization stamp not found", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LegalizationStampDTO>>> getAllLegalizationStamps() {
        try {
            List<LegalizationStampDTO> stamps = legalizationStampService.getAllLegalizationStamps();
            return ResponseEntity.ok(ApiResponse.success(stamps));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch legalization stamps", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LegalizationStampDTO>> updateLegalizationStamp(
            @PathVariable Long id,
            @RequestBody LegalizationStamp stamp) {
        try {
            LegalizationStampDTO stampDTO = legalizationStampService.updateLegalizationStamp(id, stamp);
            return ResponseEntity.ok(ApiResponse.success(stampDTO, "Legalization stamp updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Legalization stamp not found", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLegalizationStamp(@PathVariable Long id) {
        try {
            legalizationStampService.deleteLegalizationStamp(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Legalization stamp deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Legalization stamp not found", e.getMessage()));
        }
    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<ApiResponse<LegalizationStampDTO>> verify(@PathVariable String code) {
        return ResponseEntity.ok(ApiResponse.success(legalizationStampService.getByCode(code)));
    }

    @PutMapping("/{id}/image")
    public LegalizationStampDTO uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return legalizationStampService.updateImage(id, file);
    }

    @GetMapping("/{id}/qr")
    public ResponseEntity<byte[]> getQRCode(@PathVariable Long id) throws Exception {

        LegalizationStamp stamp = legalizationStampRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy stamp"));

        // Link khi scan QR
        String qrContent = "https://ccidtest.onrender.com/verify/" + stamp.getVerificationCode();

        byte[] qrImage = qrCodeService.generateQRCode(qrContent, 2000, 2000);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"stamp-" + stamp.getId() + ".png\"")
                .contentType(MediaType.IMAGE_PNG)
                .body(qrImage);
    }
}
