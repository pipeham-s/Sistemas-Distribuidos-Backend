package com.example.backend_sistemas_distribuidos.business.entities;




import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "clases")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;
    private String hora;

    // Relación con Materia
    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    // Relación con Alumno (Recibe la clase)
    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    // Relación con Profesor (Usuario que dicta la clase)
    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Usuario profesor;


}

