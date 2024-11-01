package com.example.backend_sistemas_distribuidos.persistance;

// importar la entidad materia
import com.example.backend_sistemas_distribuidos.business.entities.Materia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MateriaRepository extends CrudRepository<Materia, Long> {

    Optional<Materia> findOneById(Long id);

    Optional<Materia> findOneByNombre(String nombre);

    boolean existsByNombre(String nombre);

    Iterable<Materia> findAll();
}
