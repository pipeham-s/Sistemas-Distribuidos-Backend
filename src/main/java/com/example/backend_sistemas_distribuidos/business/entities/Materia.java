package com.example.backend_sistemas_distribuidos.business.entities;


import jakarta.persistence.*;


import java.util.List;

@Entity
@Table(name = "materias")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;


}

