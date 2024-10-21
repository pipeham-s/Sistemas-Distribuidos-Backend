package com.example.backend_sistemas_distribuidos.business.entities.auxiliar;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String correo;

    private String password;

    private String nombre;

    private String apellido;

    private String cedula;





}
