package com.example.backend_sistemas_distribuidos.business.controllers.auth;

import com.example.backend_sistemas_distribuidos.business.entities.Role;
import com.example.backend_sistemas_distribuidos.business.entities.Usuario;
import com.example.backend_sistemas_distribuidos.business.entities.auxiliar.AuthResponse;
import com.example.backend_sistemas_distribuidos.business.entities.auxiliar.LoginRequest;
import com.example.backend_sistemas_distribuidos.business.entities.auxiliar.RegisterRequest;
import com.example.backend_sistemas_distribuidos.business.utils.JwtService;
import com.example.backend_sistemas_distribuidos.persistance.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {
        Usuario usuario = Usuario.builder()
                .correo(request.getCorreo())
                .password((request.getPassword()))
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .cedula(request.getCedula())
                .role(Role.USER)
                .build();
        userRepository.save(usuario);
        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();
    }
}
