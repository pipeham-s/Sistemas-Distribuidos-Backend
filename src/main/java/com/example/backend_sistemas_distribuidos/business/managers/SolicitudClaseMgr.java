package com.example.backend_sistemas_distribuidos.business.managers;


import com.example.backend_sistemas_distribuidos.business.entities.*;
import com.example.backend_sistemas_distribuidos.business.exceptions.EntidadNoExiste;
import com.example.backend_sistemas_distribuidos.business.exceptions.InvalidInformation;
import com.example.backend_sistemas_distribuidos.persistance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SolicitudClaseMgr {

    @Autowired
    private SolicitudClaseRepository solicitudClaseRepository;

    @Autowired
    private  ClaseRepository claseRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    /**
     * Crea una nueva solicitud de habilitación de materia.
     *
     * @param cedulaUsuario Cédula del usuario .
     * @param nombreMateria ID de la materia al que se solicita la habilitación.
     * @return La solicitud creada.
     * @throws EntidadNoExiste Si el usuario o materia no existen.
     */
    public void crearSolicitudMateria(Long cedulaAlumno, Long cedulaProfesor, String nombreMateria, Date fechaSolicitud) throws EntidadNoExiste, InvalidInformation {

        // Validar parámetros de entrada
        if (cedulaAlumno == null || nombreMateria == null ||cedulaProfesor==null|| nombreMateria.trim().isEmpty()) {
            throw new InvalidInformation("Cédula del usuario y nombre de la materia no pueden ser nulos o vacíos.");
        }

        Alumno alumno = alumnoRepository.findOneByCedula(cedulaAlumno).orElseThrow(()
                -> new EntidadNoExiste("Alumno de cedula " + cedulaAlumno + "no existe"));

        Alumno profesor = alumnoRepository.findOneByCedula(cedulaProfesor).orElseThrow(()
                -> new EntidadNoExiste("Alumno de cedula " + cedulaProfesor + "no existe"));

        Materia materia = materiaRepository.findOneByNombre(nombreMateria).orElseThrow(()
                -> new EntidadNoExiste((nombreMateria + "no existe")));


        SolicitudClase solicitudClase = SolicitudClase.builder()
                .estado(SolicitudClase.EstadoSolicitud.PENDIENTE)
                .alumno(alumno)
                .profesor(profesor)
                .materia(materia)
                .fechaSolicitud(fechaSolicitud)
                .build();

        try {
            solicitudClaseRepository.save(solicitudClase);
            System.out.println("Solicitud guardada");
        } catch (Exception e) {
            System.out.println("Error al guardar la solicitud");
        }



        }
    public List<SolicitudClase> obtenerSolicitudesClasePorPersona(Long cedula) throws EntidadNoExiste {
        Alumno alumno = alumnoRepository.findOneByCedula(cedula).orElseThrow(() -> new EntidadNoExiste("alumno no encontrada."));
        System.out.println(alumno.getClasesRecibidas());
        return solicitudClaseRepository.findAllByAlumno(alumno);
    }

    public List<SolicitudClase> obtenerSolicitudesClasePorProfesor(Long cedulaProfesor) throws EntidadNoExiste {
        Alumno profesor = alumnoRepository.findOneByCedula(cedulaProfesor)
                .orElseThrow(() -> new EntidadNoExiste("Profesor con cédula " + cedulaProfesor + " no existe."));
        return solicitudClaseRepository.findAllByProfesorAndEstado(profesor, SolicitudClase.EstadoSolicitud.PENDIENTE);
    }
    public void aprobarSolicitudClase (Long idSolicitud) throws EntidadNoExiste {
        SolicitudClase solicitudClase = solicitudClaseRepository.findById(idSolicitud)
                .orElseThrow(() -> new EntidadNoExiste("Solicitud no encontrada."));
        Alumno alumno = solicitudClase.getAlumno();
        Alumno profesor = solicitudClase.getProfesor();
        Materia materia = solicitudClase.getMateria();
        Date fecha = solicitudClase.getFechaSolicitud();
        System.out.println(solicitudClase.getEstado());
        solicitudClase.setEstado(SolicitudClase.EstadoSolicitud.APROBADA);
        System.out.println(solicitudClase.getEstado());
        Clase clase = Clase.builder()
                .alumno(alumno)
                .profesor(profesor)
                .materia(materia)
                .fecha(fecha)
                .build();
        claseRepository.save(clase);
        alumno.getClasesRecibidas().add(clase);
        profesor.getClasesImpartidas().add(clase);;
        alumnoRepository.save(alumno);
        alumnoRepository.save(profesor);
        solicitudClaseRepository.save(solicitudClase);
        System.out.println(alumno.getClasesRecibidas());
        System.out.println(profesor.getClasesImpartidas());



    }



}
