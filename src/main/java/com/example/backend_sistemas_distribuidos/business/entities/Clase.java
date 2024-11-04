package com.example.backend_sistemas_distribuidos.business.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "clases")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;

    // Relación con Materia
    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    // Relación con Alumno (Recibe la clase)
    @ManyToOne
    @JoinColumn(name = "alumno_id")
    @JsonIgnoreProperties({"clasesRecibidas", "clasesImpartidas", "materiasHabilitadas"})    private Alumno alumno;

    // Relación con Profesor (Usuario que dicta la clase)
    @ManyToOne
    @JoinColumn(name = "profesor_id")
    @JsonIgnoreProperties({"clasesImpartidas", "clasesRecibidas", "materiasHabilitadas"})
    private Alumno profesor;
}




