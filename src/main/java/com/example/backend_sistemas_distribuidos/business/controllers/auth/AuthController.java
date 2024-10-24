package com.example.backend_sistemas_distribuidos.business.controllers.auth;

import com.example.backend_sistemas_distribuidos.business.entities.Usuario;
import com.example.backend_sistemas_distribuidos.business.entities.auxiliar.AuthResponse;
import com.example.backend_sistemas_distribuidos.business.entities.auxiliar.LoginRequest;
import com.example.backend_sistemas_distribuidos.business.entities.auxiliar.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Permitir solo este origen
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }
        @GetMapping("/me")
        public ResponseEntity<Usuario> getMe(Authentication authentication) {
            Usuario usuario = (Usuario) authentication.getPrincipal(); // Obtener el usuario desde el token
            return ResponseEntity.ok(usuario);
        }
    }


