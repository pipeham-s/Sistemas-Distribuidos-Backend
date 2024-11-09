package com.example.backend_sistemas_distribuidos.business.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Alumno sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Alumno receiver;

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    @JsonBackReference(value = "conversation-messages")
    private Conversation conversation;

    @Column(nullable = false)
    private String content;
}



