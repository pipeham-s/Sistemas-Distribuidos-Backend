package com.example.backend_sistemas_distribuidos.business.controllers;

import com.example.backend_sistemas_distribuidos.business.entities.Usuario;
import com.example.backend_sistemas_distribuidos.business.entities.auxiliar.LoginRequest;
import com.example.backend_sistemas_distribuidos.business.exceptions.EntidadNoExiste;
import com.example.backend_sistemas_distribuidos.business.exceptions.InvalidInformation;
import com.example.backend_sistemas_distribuidos.business.managers.UsuarioMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios") // Definir la ruta base para los endpoints del usuario
public class UsuarioController {

    @Autowired
    private UsuarioMgr usuarioManager;

    // Endpoint para validar login



        // Endpoint para validar login usando @RequestBody
        @PostMapping("/login")
        public ResponseEntity<?> validarLogin(@RequestBody LoginRequest loginRequest) {
            try {
                // Extraer los datos de loginRequest
                String correo = loginRequest.getCorreo();
                String contrasena = loginRequest.getContrasena();


                Usuario usuario = usuarioManager.validarLogin(correo, contrasena);
                return ResponseEntity.ok(usuario); // Devolver el usuario como respuesta
            } catch (EntidadNoExiste e) {
                // Retornar un mensaje de error si el usuario no existe
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            } catch (InvalidInformation e) {
                // Retornar un mensaje de error si la contraseña es incorrecta
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
            } catch (Exception e) {
                // Retornar un mensaje genérico para cualquier otra excepción
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el sistema");
            }
        }
    }

