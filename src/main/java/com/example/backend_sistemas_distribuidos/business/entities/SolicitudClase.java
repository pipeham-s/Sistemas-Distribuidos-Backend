package com.example.backend_sistemas_distribuidos.business.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "solicitudes_clases")
public class SolicitudClase {




        @Id
        @GeneratedValue
        @GenericGenerator(name = "solicitud_clase_id", strategy = "increment")
        private Long id;


        @Column(name = "estado_solicitud",nullable = false)
        @Enumerated(EnumType.STRING)
        private com.example.backend_sistemas_distribuidos.business.entities.SolicitudClase.EstadoSolicitud estado;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "alumno_cedula", nullable = false)
        @JsonManagedReference
        private Alumno alumno;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "profesor_cedula", nullable = false)
        @JsonManagedReference
        private Alumno profesor;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "materia_id", nullable = false)
        @JsonManagedReference
        private Materia materia;

        @Column(name = "fecha_solicitud", nullable = false)
        private Date fechaSolicitud;

        public enum EstadoSolicitud {
            PENDIENTE,
            APROBADA,
            RECHAZADA,
            RECHAZADA_EXP
        }
    }


