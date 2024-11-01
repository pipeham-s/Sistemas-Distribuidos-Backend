package com.example.backend_sistemas_distribuidos.business.managers;

import com.example.backend_sistemas_distribuidos.business.entities.Alumno;
import com.example.backend_sistemas_distribuidos.persistance.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumnoMgr {

    @Autowired
    private AlumnoRepository alumnoRepository;

    /**
     * Dado el nombre de una materia, devuelve una lista con los nombres de todos los alumnos que tienen esa materia
     * en su lista de materias que pueden impartir como profesor.
     *
     * @param nombreMateria Nombre de la materia.
     * @return Lista de nombres de los alumnos que pueden impartir la materia.
     */
    public List<String> obtenerAlumnosPorMateria(String nombreMateria) {
        return ((List<Alumno>) alumnoRepository.findAll()).stream()
                .filter(alumno -> alumno.getMateriasHabilitadas().stream()
                        .anyMatch(materia -> materia.getNombre().equalsIgnoreCase(nombreMateria)))
                .map(Alumno::getNombre)
                .collect(Collectors.toList());
    }
}