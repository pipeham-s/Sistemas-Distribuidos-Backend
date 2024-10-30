package com.example.backend_sistemas_distribuidos.persistance;

// importar la entidad materia
import com.example.backend_sistemas_distribuidos.business.entities.Materia;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;


public interface MateriaRepository extends CrudRepository<Materia, Long> {

    Optional<Materia> findOneById(Long id);

    Optional<Materia> findOneByNombre(String nombre);

    boolean existsByNombre(String nombre);

    Iterable<Materia> findAll();
}
