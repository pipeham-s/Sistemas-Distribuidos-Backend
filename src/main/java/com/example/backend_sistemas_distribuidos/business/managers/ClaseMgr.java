package com.example.backend_sistemas_distribuidos.business.managers;


import com.example.backend_sistemas_distribuidos.business.entities.Alumno;
import com.example.backend_sistemas_distribuidos.business.entities.Clase;
import com.example.backend_sistemas_distribuidos.business.exceptions.EntidadNoExiste;
import com.example.backend_sistemas_distribuidos.persistance.AlumnoRepository;
import com.example.backend_sistemas_distribuidos.persistance.ClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaseMgr {

    @Autowired
    ClaseRepository claseRepository;

    @Autowired
    AlumnoRepository alumnoRepository;

    public List<Clase> obtenerClasesProfesor(Long cedula) throws EntidadNoExiste {
        Alumno profesor = alumnoRepository.findOneByCedula(cedula)
                .orElseThrow(()-> new EntidadNoExiste("alumno no encontrada."));
        List<Clase> clasesProfesor = profesor.getClasesImpartidas();


        return clasesProfesor;
    }
    public List<Clase> obtenerClasesAlumno(Long cedula) throws EntidadNoExiste {
        Alumno alumno = alumnoRepository.findOneByCedula(cedula)
                .orElseThrow(()-> new EntidadNoExiste("alumno no encontrada."));
        List<Clase> clasesAlumno = alumno.getClasesRecibidas();
        return clasesAlumno;
    }
}
