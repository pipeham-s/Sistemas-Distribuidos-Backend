package com.example.backend_sistemas_distribuidos.business.controllers; // Ajusta esto a tu paquete real

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api") // Ruta base para este controlador
public class TestController {

    @GetMapping("/test") // Ruta para el endpoint /api/test
    public ResponseEntity<Map<String, String>> testEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "¡Conexión exitosa!");
        return ResponseEntity.ok(response); // Retorna la respuesta
    }
}
