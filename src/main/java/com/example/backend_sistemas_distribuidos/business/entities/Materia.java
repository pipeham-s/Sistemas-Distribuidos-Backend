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

    // Relación N a N con Alumno (alumnos habilitados a dar clases de esta materia)
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "materia_alumno",
            joinColumns = @JoinColumn(name = "materia_id"),
            inverseJoinColumns = @JoinColumn(name = "alumno_id")
    )
    private List<Alumno> alumnosHabilitados;


}

