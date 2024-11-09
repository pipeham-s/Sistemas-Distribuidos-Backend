package com.example.backend_sistemas_distribuidos.business.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_one_id", nullable = false)
    private Alumno participantOne;

    @ManyToOne
    @JoinColumn(name = "participant_two_id", nullable = false)
    private Alumno participantTwo;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "conversation-messages")
    private List<ChatMessage> messages;
}
