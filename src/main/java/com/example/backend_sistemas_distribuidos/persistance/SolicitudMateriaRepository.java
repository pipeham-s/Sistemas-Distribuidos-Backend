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
Optional<List<SolicitudMateria>> findAllByUsuarioCedula(Long cedula);

    List<SolicitudMateria> findAllByMateriaId(Long id);

    List<SolicitudMateria> findAllByEstado(SolicitudMateria.EstadoSolicitud estado);

    Optional<SolicitudMateria> findByUsuarioAndMateria(Usuario usuario, Materia materia);

    @Query("SELECT sm FROM SolicitudMateria sm JOIN FETCH sm.usuario u JOIN FETCH sm.materia m WHERE sm.id = :id")
    Optional<SolicitudMateria> findByIdWithUsuarioAndMateria(@Param("id") Long id);
}
