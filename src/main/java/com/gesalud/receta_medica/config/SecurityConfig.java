package com.gesalud.receta_medica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Usa tu WebConfig para CORS
                .and()
                .csrf().disable() // Desactiva CSRF solo para pruebas
                .authorizeHttpRequests()
                .anyRequest().permitAll(); // Permite TODO

        return http.build();
    }
}
