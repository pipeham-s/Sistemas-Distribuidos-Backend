package com.example.backend_sistemas_distribuidos.persistance;

import com.example.backend_sistemas_distribuidos.business.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SolicitudClaseRepository extends CrudRepository<SolicitudClase, Long> {

    List<SolicitudClase> findAllByMateriaId(Long id);

    List<SolicitudClase> findAllByAlumno(Alumno alumno);


    List<SolicitudClase> findAllByEstado(SolicitudClase.EstadoSolicitud estado);

    Optional<SolicitudClase> findByAlumnoAndMateria(Usuario alumno, Materia materia);

    Optional<SolicitudClase> findSolicitudMateiraById(Long id);

}
