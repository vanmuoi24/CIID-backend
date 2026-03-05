package com.example.ccidbackend.controller;

import com.example.ccidbackend.dto.ApiResponse;
import com.example.ccidbackend.dto.LegalizationStampDTO;
import com.example.ccidbackend.entity.LegalizationStamp;
import com.example.ccidbackend.service.LegalizationStampService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/legalization-stamps")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class LegalizationStampController {

    private final LegalizationStampService legalizationStampService;

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
    public ResponseEntity<ApiResponse<LegalizationStampDTO>> getLegalizationStampByStampNumber(@PathVariable String stampNumber) {
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
}
