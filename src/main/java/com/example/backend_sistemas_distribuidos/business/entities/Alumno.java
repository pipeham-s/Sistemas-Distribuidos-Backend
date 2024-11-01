package com.example.backend_sistemas_distribuidos.business.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "alumnos")
@Getter
@Setter
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

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
    @JsonManagedReference
    private List<Materia> materiasHabilitadas;

    }
