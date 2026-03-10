package com.example.ccidbackend.controller;

import com.example.ccidbackend.dto.ApiResponse;
import com.example.ccidbackend.dto.SignatureDTO;
import com.example.ccidbackend.entity.Signature;
import com.example.ccidbackend.service.SignatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/signatures")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class SignatureController {

    private final SignatureService signatureService;

    @PostMapping
    public ResponseEntity<ApiResponse<SignatureDTO>> createSignature(@RequestBody Signature signature) {
        try {
            SignatureDTO dto = signatureService.createSignature(signature);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(dto, "Signature created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to create signature", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SignatureDTO>> getSignatureById(@PathVariable Long id) {
        try {
            SignatureDTO dto = signatureService.getSignatureById(id);
            return ResponseEntity.ok(ApiResponse.success(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Signature not found", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SignatureDTO>>> getAllSignatures() {
        try {
            List<SignatureDTO> list = signatureService.getAllSignatures();
            return ResponseEntity.ok(ApiResponse.success(list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch signatures", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SignatureDTO>> updateSignature(
            @PathVariable Long id,
            @RequestBody Signature signature
    ) {
        try {
            SignatureDTO dto = signatureService.updateSignature(id, signature);
            return ResponseEntity.ok(ApiResponse.success(dto, "Signature updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Signature not found", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSignature(@PathVariable Long id) {
        try {
            signatureService.deleteSignature(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Signature deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Signature not found", e.getMessage()));
        }
    }
}