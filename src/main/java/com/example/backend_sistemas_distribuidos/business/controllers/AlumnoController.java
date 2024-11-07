package com.example.backend_sistemas_distribuidos.business.controllers;

import com.example.backend_sistemas_distribuidos.business.managers.AlumnoMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoMgr alumnoMgr;

    private static final Logger logger = Logger.getLogger(AlumnoController.class.getName());

    /**
     * Endpoint para obtener una lista con los nombres de todos los alumnos que pueden impartir una materia específica.
     *
     * @param nombreMateria Nombre de la materia.
     * @return Lista de nombres de los alumnos que pueden impartir la materia.
     */
    @GetMapping("/por-materia")
    public List<Map<String, Object>> obtenerAlumnosPorMateria(@RequestParam String nombreMateria, Long cedula) {
        logger.info("Nombre de la materia recibido: " + nombreMateria);
        List<Map<String, Object>> alumnos = alumnoMgr.obtenerAlumnosPorMateria(nombreMateria,cedula);
        if (alumnos.isEmpty()) {
            logger.info("No se encontraron alumnos para la materia: " + nombreMateria);
        } else {
            logger.info("Alumnos encontrados: " + alumnos);
        }
        return alumnos;
    }
}




