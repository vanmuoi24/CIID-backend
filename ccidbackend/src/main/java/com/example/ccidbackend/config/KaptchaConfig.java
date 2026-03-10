package com.example.ccidbackend.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha captchaProducer() {

        Properties properties = new Properties();

        // kích thước ảnh
        properties.put("kaptcha.image.width", "290");
        properties.put("kaptcha.image.height", "50");
        properties.put("kaptcha.border", "no");


        // font chữ
        properties.put("kaptcha.textproducer.font.names", "Serif");
        properties.put("kaptcha.textproducer.font.size", "35");

        // màu chữ xanh
        properties.put("kaptcha.textproducer.font.color", "69,92,163");

        // số ký tự
        properties.put("kaptcha.textproducer.char.length", "6");

        // nền xám nhạt
        properties.put("kaptcha.background.clear.from", "242,242,242");
        properties.put("kaptcha.background.clear.to", "242,242,242");

        // thêm nhiễu chấm
        properties.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
        properties.put("kaptcha.noise.color", "150,150,150");

        // cong chữ

        Config config = new Config(properties);

        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        captchaProducer.setConfig(config);

        return captchaProducer;
    }
}