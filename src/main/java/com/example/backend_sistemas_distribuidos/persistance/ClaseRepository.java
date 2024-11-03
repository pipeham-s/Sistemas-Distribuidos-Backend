package com.example.backend_sistemas_distribuidos.persistance;

import com.example.backend_sistemas_distribuidos.business.entities.Clase;
import org.springframework.data.repository.CrudRepository;

public interface ClaseRepository extends CrudRepository<Clase, Long> {
}
