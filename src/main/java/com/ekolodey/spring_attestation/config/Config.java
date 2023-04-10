package com.ekolodey.spring_attestation.config;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class Config implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadDir = new File(uploadPath).getAbsolutePath();
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///" + uploadDir + "/");

    }
}
