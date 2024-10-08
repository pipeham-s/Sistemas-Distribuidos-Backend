package com.example.backend_sistemas_distribuidos.business.managers;

import com.example.backend_sistemas_distribuidos.business.entities.Usuario;
import com.example.backend_sistemas_distribuidos.business.exceptions.EntidadNoExiste;
import com.example.backend_sistemas_distribuidos.business.exceptions.InvalidInformation;
import com.example.backend_sistemas_distribuidos.persistance.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
