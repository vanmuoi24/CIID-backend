package com.example.ccidbackend.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class CaptchaController {

    @Autowired
    private DefaultKaptcha captchaProducer;

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, HttpSession session) throws Exception {

        // tạo text captcha
        String text = captchaProducer.createText();

        // lưu vào session để verify
        session.setAttribute("captcha", text);

        // tạo ảnh captcha
        BufferedImage image = captchaProducer.createImage(text);

        response.setContentType("image/png");

        ImageIO.write(image, "png", response.getOutputStream());
    }
}