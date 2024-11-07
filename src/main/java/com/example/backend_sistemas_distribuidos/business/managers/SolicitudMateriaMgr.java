package com.example.backend_sistemas_distribuidos.business.managers;

import com.example.backend_sistemas_distribuidos.business.entities.Alumno;
import com.example.backend_sistemas_distribuidos.business.entities.SolicitudMateria;
import com.example.backend_sistemas_distribuidos.business.entities.Usuario;
import com.example.backend_sistemas_distribuidos.business.exceptions.EntidadNoExiste;
import com.example.backend_sistemas_distribuidos.business.exceptions.InvalidInformation;
import com.example.backend_sistemas_distribuidos.persistance.AlumnoRepository;
import com.example.backend_sistemas_distribuidos.persistance.SolicitudMateriaRepository;
import com.example.backend_sistemas_distribuidos.persistance.UsuarioRepository;
import com.example.backend_sistemas_distribuidos.persistance.MateriaRepository;
import com.example.backend_sistemas_distribuidos.business.entities.Materia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SolicitudMateriaMgr {

    @Autowired
    private SolicitudMateriaRepository solicitudMateriaRepository;


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
    public void crearSolicitudMateria(Long cedulaUsuario, String nombreMateria) throws EntidadNoExiste, InvalidInformation {

        // Validar parámetros de entrada
        if (cedulaUsuario == null || nombreMateria == null || nombreMateria.trim().isEmpty()) {
            throw new InvalidInformation("Cédula del usuario y nombre de la materia no pueden ser nulos o vacíos.");
        }

        // Buscar el ofertante por su cédula
        Usuario usuario = usuarioRepository.findOneByCedula(cedulaUsuario)
            .orElseThrow(() -> new EntidadNoExiste("Usuario no encontrado con la cédula: " + cedulaUsuario));
        System.out.println(nombreMateria);
        // Buscar la materia por su nombre
        Materia materia = materiaRepository.findOneByNombre(nombreMateria.trim())
            .orElseThrow(() -> new EntidadNoExiste("Materia no encontrada con el nombre: " + nombreMateria));

        //Verificar si ya existe una solicitud pendiente para la materia de este usuario o ya lo tiene habilitado
        Optional<SolicitudMateria> solicitudExistente = solicitudMateriaRepository.findByAlumnoAndMateria(usuario, materia);
        if (solicitudExistente.isPresent()) {
            if (solicitudExistente.get().getEstado() == SolicitudMateria.EstadoSolicitud.PENDIENTE) {
                throw new InvalidInformation("Ya existe una solicitud pendiente para la materia: " + nombreMateria);
            }
            else if(solicitudExistente.get().getEstado() == SolicitudMateria.EstadoSolicitud.APROBADA) {
                throw new InvalidInformation("Ya tiene la materia habilitada: " + nombreMateria);
            }

        }

        // Crear la solicitud
        SolicitudMateria solicitud = SolicitudMateria.builder()
            .alumno(usuario)
            .materia(materia)
            .estado(SolicitudMateria.EstadoSolicitud.PENDIENTE)
            .build();

        System.out.println(solicitud.getMateria().getNombre() + solicitud.getAlumno().getNombre());
        // Guardar la solicitud
        try {
            solicitudMateriaRepository.save(solicitud);
            System.out.println("Solicitud guardada");
        } catch (Exception e) {
            System.out.println("Error al guardar la solicitud");
        }
    }

    /**
     * Aprueba una solicitud de habilitación de materia.
     *
     * @param idSolicitud ID de la solicitud a aprobar.
     * @throws EntidadNoExiste Si la solicitud no existe.
     */
    public void aprobarSolicitud(Long idSolicitud) throws EntidadNoExiste {
        // Buscar la solicitud por su ID
        SolicitudMateria solicitud = solicitudMateriaRepository.findByIdWithUsuarioAndMateria(idSolicitud).orElseThrow(() -> new EntidadNoExiste("Solicitud no encontrada."));
        Usuario usuario = solicitud.getAlumno();
        Materia materia = solicitud.getMateria();
        usuario.agregarMateria(materia);
        solicitud.setEstado(SolicitudMateria.EstadoSolicitud.APROBADA);
        solicitudMateriaRepository.save(solicitud);
        usuarioRepository.save(usuario);
    }

    public List<SolicitudMateria> obtenerSolicitudesPendientes() {
        return solicitudMateriaRepository.findAllByEstado(SolicitudMateria.EstadoSolicitud.PENDIENTE);
    }
    public List<SolicitudMateria> obtenerSolicitudesPersona(Long cedula) throws EntidadNoExiste {
        Alumno alumno = alumnoRepository.findOneByCedula(cedula).orElseThrow(() -> new EntidadNoExiste("Solicitud no encontrada."));
        return solicitudMateriaRepository.findAllByAlumno(alumno);
    }

    public void rechazarSolicitud(Long idSolicitud) throws EntidadNoExiste {
        SolicitudMateria solicitud = solicitudMateriaRepository.findByIdWithUsuarioAndMateria(idSolicitud).orElseThrow(() -> new EntidadNoExiste("Solicitud no encontrada."));
        solicitud.setEstado(SolicitudMateria.EstadoSolicitud.RECHAZADA);
        solicitudMateriaRepository.save(solicitud);
    }
}
