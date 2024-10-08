package com.example.backend_sistemas_distribuidos.persistance;
import com.example.backend_sistemas_distribuidos.business.entities.Usuario;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findOneByCorreo(String nombre);
}
