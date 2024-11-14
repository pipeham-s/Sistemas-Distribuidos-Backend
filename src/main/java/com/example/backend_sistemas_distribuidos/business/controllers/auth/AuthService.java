package com.example.backend_sistemas_distribuidos.business.controllers.auth;

import com.example.backend_sistemas_distribuidos.business.entities.Alumno;
import com.example.backend_sistemas_distribuidos.business.entities.Role;
import com.example.backend_sistemas_distribuidos.business.entities.Usuario;
import com.example.backend_sistemas_distribuidos.business.entities.auxiliar.AuthResponse;
import com.example.backend_sistemas_distribuidos.business.entities.auxiliar.LoginRequest;
import com.example.backend_sistemas_distribuidos.business.entities.auxiliar.RegisterRequest;
import com.example.backend_sistemas_distribuidos.business.utils.JwtService;
import com.example.backend_sistemas_distribuidos.persistance.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.backend_sistemas_distribuidos.business.entities.Alumno;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;



    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        Usuario user =  userRepository.findByCorreo(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        Alumno alumno = Alumno.builder()
                .correo(request.getCorreo())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .cedula(request.getCedula())
                .role(Role.USER)
                .build();
        userRepository.save(alumno);
        return AuthResponse.builder()
                .token(jwtService.getToken(alumno))
                .build();
    }
}
