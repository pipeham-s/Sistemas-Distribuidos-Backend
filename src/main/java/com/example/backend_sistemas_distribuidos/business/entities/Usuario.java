package com.example.backend_sistemas_distribuidos.business.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)  // Permite herencia con Alumnos y Administradores
@Getter
@Setter
public class Usuario {

    @Id
    @Column(unique = true, nullable = false)  // Asegura que el nombre sea único y no nulo
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)  // Asegura que el nombre sea único y no nulo
    private String nombre;
    @Column(unique = true, nullable = false)  // Asegura que el nombre sea único y no nulo
    private String apellido;
    @Column(unique = true, nullable = false)  // Asegura que el nombre sea único y no nulo
    private String cedula;
    @Column(unique = true, nullable = false)  // Asegura que el nombre sea único y no nulo
    private String correo;
    @Column(unique = true, nullable = false)  // Asegura que el nombre sea único y no nulo
    private String contrasena;


}

