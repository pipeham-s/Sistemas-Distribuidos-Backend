package com.example.backend_sistemas_distribuidos.business.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "alumnos")
@Getter
@Setter
public class Alumno extends Usuario {

    // Relación con Clase (clases recibidas)
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Clase> clasesRecibidas;

    // Relación con Clase (clases impartidas)
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Clase> clasesImpartidas;

    // Relación con Materia (materias habilitadas)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "materias_de_un_alumno", joinColumns = @JoinColumn(name = "alumno_id"), inverseJoinColumns = @JoinColumn(name = "materia_id"))
    private List<Materia> materiasHabilitadas;

    }
