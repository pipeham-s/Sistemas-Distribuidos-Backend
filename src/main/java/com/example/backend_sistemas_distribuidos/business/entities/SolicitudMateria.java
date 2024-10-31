package com.example.backend_sistemas_distribuidos.business.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "solicitudes_materias")
public class SolicitudMateria {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "solicitud_materia_id", strategy = "increment")
    private Long id;


    @Column(name = "estado_solicitud",nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alumno_id", nullable = false)
    @JsonManagedReference
    private Usuario alumno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "materia_id", nullable = false)
    @JsonManagedReference
    private Materia materia;

    public enum EstadoSolicitud {
        PENDIENTE,
        APROBADA,
        RECHAZADA,
        RECHAZADA_EXP
    }
}
