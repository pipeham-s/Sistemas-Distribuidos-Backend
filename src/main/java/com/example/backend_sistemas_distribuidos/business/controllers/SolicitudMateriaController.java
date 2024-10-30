package com.example.backend_sistemas_distribuidos.business.controllers;

import com.example.backend_sistemas_distribuidos.business.managers.SolicitudMateriaMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.backend_sistemas_distribuidos.business.exceptions.EntidadNoExiste;
import com.example.backend_sistemas_distribuidos.business.exceptions.InvalidInformation;


@RestController
@RequestMapping("/api/solicitud-materia")
@CrossOrigin(origins = "http://localhost:3000")
public class SolicitudMateriaController {

    @Autowired
    private SolicitudMateriaMgr solicitudMateriaMgr;

    // Declarar el logger
    private static final Logger logger = LoggerFactory.getLogger(SolicitudMateriaController.class);

    @PostMapping("/crear")
    public ResponseEntity<?> crearSolicitudMateria(@RequestBody Long cedulaUsuario, String nombreMateria) {
        try {
            // Validar y desglosar el payload
            if (cedulaUsuario == null || nombreMateria == null || nombreMateria.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos incompletos para crear la solicitud.");
            }

            // Log para verificación de los valores antes de crear la solicitud
            logger.info("Intentando crear solicitud para cédula: {}, materia: {}", cedulaUsuario, nombreMateria);

            // Llamar al manager para crear la solicitud
            solicitudMateriaMgr.crearSolicitudMateria(cedulaUsuario, nombreMateria);

            // Log para seguimiento
            logger.info("Solicitud de materia creada para usuario con cédula: {}", cedulaUsuario);

            // Retornar la respuesta
            return ResponseEntity.ok("Solicitud de materia creada correctamente");
        } catch (EntidadNoExiste | InvalidInformation e) {
            // Log del error con mensaje específico
            logger.error("Error al crear solicitud de materia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NumberFormatException e) {
            // Manejo específico si hay un error al convertir los datos
            logger.error("Error de formato en los datos recibidos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en los datos de entrada: " + e.getMessage());
        } catch (Exception e) {
            // Log de errores generales no manejados
            logger.error("Error inesperado al crear solicitud de materia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al crear solicitud de materia");
        }
    }

    @GetMapping("/pendientes")
    public ResponseEntity<?> obtenerSolicitudesPendientes() {

    }





}
