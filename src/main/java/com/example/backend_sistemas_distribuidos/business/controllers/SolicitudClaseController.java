package com.example.backend_sistemas_distribuidos.business.controllers;

import com.example.backend_sistemas_distribuidos.business.entities.SolicitudClase;
import com.example.backend_sistemas_distribuidos.business.entities.SolicitudMateria;
import com.example.backend_sistemas_distribuidos.business.managers.SolicitudClaseMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.backend_sistemas_distribuidos.business.exceptions.EntidadNoExiste;
import com.example.backend_sistemas_distribuidos.business.exceptions.InvalidInformation;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Date;

@RestController
@RequestMapping("/api/solicitud-clase")
@CrossOrigin(origins = "http://localhost:3000")
public class SolicitudClaseController {

    @Autowired
    private SolicitudClaseMgr solicitudClaseMgr;

    // Declarar el logger
    private static final Logger logger = LoggerFactory.getLogger(SolicitudClaseController.class);

    @PostMapping("/crear")
    public ResponseEntity<?> crearSolicitudClase(@RequestBody Map<String, Object> payload) {
        try {
            Long cedulaAlumno = payload.get("cedulaAlumno") == null ? null : Long.parseLong(payload.get("cedulaAlumno").toString().trim());
            Long cedulaProfesor = payload.get("cedulaProfesor") == null ? null : Long.parseLong(payload.get("cedulaProfesor").toString().trim());
            String nombreMateria = (String) payload.get("nombreMateria");
            Date fechaSolicitud = payload.get("fecha") == null ? null : new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(payload.get("fecha").toString());

            // Log para verificación de lo  s valores antes de crear la solicitud
            logger.info("Intentando crear solicitud de clase para alumno cédula: {}, profesor cédula: {}, materia: {}", cedulaAlumno, cedulaProfesor, nombreMateria);

            // Llamar al manager para crear la solicitud
            solicitudClaseMgr.crearSolicitudMateria(cedulaAlumno, cedulaProfesor, nombreMateria, fechaSolicitud);

            // Log para seguimiento
            logger.info("Solicitud de clase creada para alumno con cédula: {}", cedulaAlumno);

            // Retornar la respuesta
            return ResponseEntity.ok("Solicitud de clase creada correctamente");
        } catch (EntidadNoExiste | InvalidInformation e) {
            // Log del error con mensaje específico
            logger.error("Error al crear solicitud de clase: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NumberFormatException e) {
            // Manejo específico si hay un error al convertir los datos
            logger.error("Error de formato en los datos recibidos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en los datos de entrada: " + e.getMessage());
        } catch (Exception e) {
            // Log de errores generales no manejados
            logger.error("Error inesperado al crear solicitud de clase: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al crear solicitud de clase");
        }
    }
    @GetMapping("/pendientes{cedula}")
    public ResponseEntity<List<SolicitudClase>> obtenerSolicitudesPendientesPorCedula(@PathVariable Long cedula) {
        try {
            // Llamar al manager para obtener las solicitudes pendientes de la cédula proporcionada
            List<SolicitudClase> solicitudesPendientes = solicitudClaseMgr.obtenerSolicitudesClasePorPersona(cedula);
            logger.info("Solicitudes pendientes obtenidas para la cédula: {}", cedula);

            // Retornar la respuesta con las solicitudes encontradas
            return ResponseEntity.ok(solicitudesPendientes);
        } catch (Exception e) {
            // Log de errores generales no manejados
            logger.error("Error inesperado al obtener solicitudes de materia pendientes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
    @GetMapping("/pendientes/profesor{cedulaProfesor}")
    public ResponseEntity<List<SolicitudClase>> obtenerSolicitudesPendientesPorProfesor(@PathVariable Long cedulaProfesor) {
        try {
            // Llamar al manager para obtener las solicitudes pendientes del profesor con la cédula proporcionada
            List<SolicitudClase> solicitudesPendientes = solicitudClaseMgr.obtenerSolicitudesClasePorProfesor(cedulaProfesor);
            logger.info("Solicitudes pendientes obtenidas para el profesor con cédula: {}", cedulaProfesor);

            // Retornar la respuesta con las solicitudes encontradas
            return ResponseEntity.ok(solicitudesPendientes);
        } catch (EntidadNoExiste e) {
            // Log del error cuando la entidad no existe
            logger.error("Error al obtener solicitudes de materia pendientes para el profesor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            // Log de errores generales no manejados
            logger.error("Error inesperado al obtener solicitudes de materia pendientes para el profesor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping("/aceptar/{id}")
    public ResponseEntity<?> aceptarClase(@PathVariable("id") Long solicitudId) {
        try {
            // Llamar al manager para aprobar la solicitud
            solicitudClaseMgr.aprobarSolicitudClase(solicitudId);

            return ResponseEntity.ok("Solicitud de clase aprobada correctamente");
        } catch (EntidadNoExiste e) {
            // Log del error con mensaje específico
            logger.error("Error al aprobar solicitud de materia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Log de errores generales no manejados
            logger.error("Error inesperado al aprobar solicitud de materia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado al aprobar solicitud de materia");
        }

    }
}

