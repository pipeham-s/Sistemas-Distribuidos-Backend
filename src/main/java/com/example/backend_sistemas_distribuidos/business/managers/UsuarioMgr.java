package com.example.backend_sistemas_distribuidos.business.managers;

import com.example.backend_sistemas_distribuidos.business.entities.Usuario;
import com.example.backend_sistemas_distribuidos.business.exceptions.EntidadNoExiste;
import com.example.backend_sistemas_distribuidos.business.exceptions.InvalidInformation;
import com.example.backend_sistemas_distribuidos.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioMgr {
    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario validarLogin(String email, String contrasena) throws InvalidInformation, EntidadNoExiste{
        Usuario usuario = usuarioRepository.findOneByCorreo(email).orElseThrow(() -> new EntidadNoExiste("Usuario no existe"));
        if (usuario.getContrasena().equals(contrasena)){
            return usuario;
        }
        else {
            throw new InvalidInformation("Contrasena invalida");
        }
    }
    public List<Usuario> obtenerTodosLosUsuarios() {
        // Usar el repositorio para obtener todos los usuarios
        return usuarioRepository.findAll();


}
    public Usuario crearUsuario(String nombre, String apellido, String cedula, String correo, String contrasena) throws InvalidInformation, EntidadNoExiste {
        // Verificar si algún campo es nulo o está vacío
        if (nombre == null || nombre.isEmpty() ||
                apellido == null || apellido.isEmpty() ||
                cedula == null || cedula.isEmpty() ||
                correo == null || correo.isEmpty() ||
                contrasena == null || contrasena.isEmpty()) {
            throw new InvalidInformation("Datos vacíos o nulos");
        }

        // Validar si el correo ya está registrado en la base de datos
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(correo);
        if (usuarioExistente.isPresent()) {
            throw new InvalidInformation("El correo ya está registrado");
        }

        // Crear nuevo usuario con la información proporcionada
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setCedula(cedula);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setContrasena(contrasena); // Idealmente se debe encriptar la contraseña antes de guardarla
        System.out.println(nombre + apellido);
        // Guardar el nuevo usuario en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        return usuarioGuardado;
    }



}
