package com.example.ccidbackend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ccidbackend.dto.SearchRequest;
import com.example.ccidbackend.entity.LegalizationStamp;
import com.example.ccidbackend.service.LegalizationStampService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/stamps")
public class StampController {

    @Autowired
    private LegalizationStampService legalizationStampService;

    @PostMapping("/search")
    public Map<String, Object> search(@RequestBody SearchRequest request,
                                      HttpSession session) {

        // Validate input
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            return Map.of(
                    "success", false,
                    "message", "Vui lòng nhập số hiệu"
            );
        }

        if (request.getCaptcha() == null || request.getCaptcha().trim().isEmpty()) {
            return Map.of(
                    "success", false,
                    "message", "Vui lòng nhập mã bảo mật"
            );
        }

        // Get captcha from session
        String captchaSession = (String) session.getAttribute("captcha");

        // Validate captcha exists in session
        if (captchaSession == null) {
            return Map.of(
                    "success", false,
                    "message", "Phiên làm việc đã hết hạn. Vui lòng tải lại trang"
            );
        }

        // Validate captcha match (case insensitive)
        if (!captchaSession.equalsIgnoreCase(request.getCaptcha().trim())) {
            // Remove captcha from session after failed attempt
            session.removeAttribute("captcha");
            
            return Map.of(
                    "success", false,
                    "message", "Mã bảo mật không đúng"
            );
        }

        // Remove captcha from session after successful validation
        session.removeAttribute("captcha");

        // Search for stamp by stampNumber and return DTO to avoid circular reference
        LegalizationStamp stamp = legalizationStampService.findByStampNumber(request.getCode().trim());

        if (stamp == null) {
            return Map.of(
                    "success", false,
                    "message", "Không tìm thấy thông tin với số hiệu này"
            );
        }

        // Convert to DTO using the service method to avoid circular reference
        try {
            var stampDTO = legalizationStampService.getLegalizationStampByStampNumber(request.getCode().trim());
            return Map.of(
                    "success", true,
                    "data", stampDTO,
                    "message", "Tra cứu thành công"
            );
        } catch (Exception e) {
            return Map.of(
                    "success", false,
                    "message", "Không tìm thấy thông tin với số hiệu này"
            );
        }
    }
}