package com.example.backend_sistemas_distribuidos.business.managers;

import com.example.backend_sistemas_distribuidos.business.entities.Alumno;
import com.example.backend_sistemas_distribuidos.persistance.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

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


    public List<Map<String, Object>> obtenerAlumnosPorMateria(String nombreMateria, Long cedula) {
        System.out.println("Buscando alumnos para la materia: " + nombreMateria);
        List<Alumno> alumnos = (List<Alumno>) alumnoRepository.findAll();
        Alumno alumno1 = alumnoRepository.findOneByCedula(cedula).get();
        System.out.println("Número total de alumnos encontrados: " + alumnos.size());

        List<Map<String, Object>> alumnosQuePuedenImpartir = alumnos.stream()
            .filter(alumno -> {
                // Verificar si el alumno actual es distinto de alumno1
                if (alumno.equals(alumno1)) {
                    System.out.println("Excluyendo al alumno: " + alumno.getNombre());
                    return false; // Excluir alumno1
                }

                System.out.println("Verificando materias habilitadas para el alumno: " + alumno.getNombre());
                boolean tieneMateria = alumno.getMateriasHabilitadas().stream()
                    .anyMatch(materia -> {
                        String materiaNombreTrimmed = materia.getNombre().trim();
                        String nombreMateriaTrimmed = nombreMateria.trim();
                        System.out.println("Comparando materia: " + materiaNombreTrimmed + " con " + nombreMateriaTrimmed);
                        return materiaNombreTrimmed.equalsIgnoreCase(nombreMateriaTrimmed);
                    });

                if (tieneMateria) {
                    System.out.println("El alumno " + alumno.getNombre() + " puede impartir la materia " + nombreMateria);
                }
                return tieneMateria;
            })
            .map(alumno -> {
                Map<String, Object> alumnoInfo = new HashMap<>();
                alumnoInfo.put("cedula", alumno.getCedula());
                alumnoInfo.put("nombreCompleto", alumno.getNombre() + " " + alumno.getApellido());
                return alumnoInfo;
            })
            .collect(Collectors.toList());


        System.out.println("Número de alumnos que pueden impartir la materia: " + alumnosQuePuedenImpartir.size());
        return alumnosQuePuedenImpartir;
    }
}