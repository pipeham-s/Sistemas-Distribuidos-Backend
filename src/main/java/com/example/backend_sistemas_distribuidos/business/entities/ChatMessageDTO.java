package com.example.backend_sistemas_distribuidos.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private Long senderId;
    private Long receiverId;
    private String content;
}
