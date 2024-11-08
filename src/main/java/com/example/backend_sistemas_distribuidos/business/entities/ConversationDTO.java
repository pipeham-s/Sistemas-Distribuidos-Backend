package com.example.backend_sistemas_distribuidos.business.entities;

import com.example.backend_sistemas_distribuidos.business.entities.Alumno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {
    private Long contactId;
    private String contactName;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
}

