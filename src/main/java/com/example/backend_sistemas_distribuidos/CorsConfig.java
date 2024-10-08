package com.example.backend_sistemas_distribuidos; // Ajusta esto según tu estructura de paquetes


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Permitir el envío de cookies
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Permitir solicitudes desde este origen
        config.setAllowedHeaders(Arrays.asList("*")); // Permitir todos los headers
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Permitir métodos HTTP

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Configurar para todos los endpoints
        return new CorsFilter(source);
    }
}

