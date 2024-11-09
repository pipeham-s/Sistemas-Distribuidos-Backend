package com.example.backend_sistemas_distribuidos.business.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {
    private Long contactId;
    private String contactName;
    private String lastMessage;
}


