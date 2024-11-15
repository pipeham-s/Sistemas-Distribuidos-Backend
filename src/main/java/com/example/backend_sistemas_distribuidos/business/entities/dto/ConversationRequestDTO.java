package com.example.backend_sistemas_distribuidos.business.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConversationRequestDTO {
    private Long initiatorId;
    private Long receiverId;
}
