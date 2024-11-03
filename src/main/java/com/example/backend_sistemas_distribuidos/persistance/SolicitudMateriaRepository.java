package com.example.backend_sistemas_distribuidos.persistance;

import com.example.backend_sistemas_distribuidos.business.entities.Materia;
import com.example.backend_sistemas_distribuidos.business.entities.SolicitudMateria;
import com.example.backend_sistemas_distribuidos.business.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface SolicitudMateriaRepository extends CrudRepository<SolicitudMateria, Long>{
    //Optional<List<SolicitudMateria>> findAllByAlumnoId(Long cedula);

    List<SolicitudMateria> findAllByMateriaId(Long id);

    List<SolicitudMateria> findAllByEstado(SolicitudMateria.EstadoSolicitud estado);

    List<SolicitudMateria> findAllByAlumno(Usuario alumno);


    Optional<SolicitudMateria> findByAlumnoAndMateria(Usuario alumno, Materia materia);

    Optional<SolicitudMateria> findSolicitudMateiraById(Long id);


    @Query("SELECT sm FROM SolicitudMateria sm JOIN FETCH sm.alumno u JOIN FETCH sm.materia m WHERE sm.id = :id")
    Optional<SolicitudMateria> findByIdWithUsuarioAndMateria(@Param("id") Long id);
}
