package com.example.backend_sistemas_distribuidos.persistance;

// importar la entidad Alumno
import com.example.backend_sistemas_distribuidos.business.entities.Alumno;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long> {

    Optional<Alumno> findOneById(Long id);

    Optional<Alumno> findOneByCedula(Long cedula);

    Optional<Alumno> findOneByNombre(String nombre);

    boolean existsByNombre(String nombre);

    Iterable<Alumno> findAll();
}
