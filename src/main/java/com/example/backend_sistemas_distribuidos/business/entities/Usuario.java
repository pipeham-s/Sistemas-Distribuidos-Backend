package com.example.backend_sistemas_distribuidos.business.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)  // Permite herencia con Alumnos y Administradores
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String cedula;
    private String correo;
    private String contrasena;
}
// Elimina la relación @OneToMany con Alumno, ya que no es necesaria para la herencia.

// Constructores, Getters y Setters
