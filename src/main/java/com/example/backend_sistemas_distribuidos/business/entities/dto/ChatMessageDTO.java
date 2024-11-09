package com.example.backend_sistemas_distribuidos.business.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private Long id;
    private Long senderId;
    private String senderName; // Nombre del remitente si lo necesitas
    private Long receiverId;
    private String receiverName; // Nombre del receptor si lo necesitas
    private String content;
    private Long conversationId;
}

