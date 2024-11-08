package com.example.backend_sistemas_distribuidos.business.controllers;

import com.example.backend_sistemas_distribuidos.business.dto.ChatMessageDTO;
import com.example.backend_sistemas_distribuidos.business.entities.ChatMessage;
import com.example.backend_sistemas_distribuidos.business.entities.Alumno;
import com.example.backend_sistemas_distribuidos.persistance.AlumnoRepository;
import com.example.backend_sistemas_distribuidos.business.managers.ChatMessageMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageMgr chatMessageMgr;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessage handleMessage(ChatMessageDTO chatMessageDTO) {
        // Buscar los alumnos remitente y receptor
        Alumno sender = alumnoRepository.findById(chatMessageDTO.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("El remitente con ID " + chatMessageDTO.getSenderId() + " no existe."));
        Alumno receiver = alumnoRepository.findById(chatMessageDTO.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("El destinatario con ID " + chatMessageDTO.getReceiverId() + " no existe."));

        // Construir y guardar el mensaje
        ChatMessage chatMessage = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .content(chatMessageDTO.getContent())
                .build();

        // Llamar al método de negocio para procesar y guardar el mensaje
        return chatMessageMgr.sendMessage(chatMessage.getSender().getId(), chatMessage.getReceiver().getId(), chatMessage.getContent());
    }
}
