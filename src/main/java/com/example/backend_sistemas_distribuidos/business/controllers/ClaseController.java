package com.example.backend_sistemas_distribuidos.business.controllers;

import com.example.backend_sistemas_distribuidos.business.exceptions.EntidadNoExiste;
import com.example.backend_sistemas_distribuidos.business.managers.SolicitudClaseMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/solicitud-clase")
@CrossOrigin(origins = "http://localhost:3000")
public class ClaseController {

    @Autowired
    private SolicitudClaseMgr solicitudClaseMgr;

    // Logger para depuración
    private static final Logger logger = LoggerFactory.getLogger(ClaseController.class);

    @PostMapping("/aprobar/{idSolicitud}")
    public ResponseEntity<String> aprobarSolicitudClase(@PathVariable Long idSolicitud) {
        try {
            // Log para verificación
            logger.info("Recibida solicitud para aprobar clase con ID: {}", idSolicitud);

            // Validación explícita del ID para evitar valores nulos o incorrectos
            if (idSolicitud == null || idSolicitud <= 0) {
                logger.error("ID de solicitud inválido: {}", idSolicitud);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID de solicitud inválido.");
            }

            // Llamar al manager para aprobar la solicitud y crear la clase
            solicitudClaseMgr.aprobarClase(idSolicitud);

            // Log para seguimiento del éxito
            logger.info("Solicitud de clase con ID {} aprobada exitosamente.", idSolicitud);

            // Retornar respuesta de éxito
            return ResponseEntity.ok("Solicitud de clase aprobada y clase creada exitosamente.");
        } catch (EntidadNoExiste e) {
            // Log del error específico si la entidad no existe
            logger.error("Error al aprobar la solicitud de clase: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Log de errores generales no manejados
            logger.error("Error inesperado al aprobar la solicitud de clase: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al aprobar la solicitud de clase.");
        }
    }
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        System.out.println("Endpoint de prueba alcanzado");
        return ResponseEntity.ok("Endpoint de prueba funcionando");
    }
}
