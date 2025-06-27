package com.gesalud.receta_medica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // permite todo
                .allowedOrigins("*")  // desde cualquier origen
                .allowedMethods("*")  // cualquier método (GET, POST, etc)
                .allowedHeaders("*"); // cualquier cabecera
    }
}