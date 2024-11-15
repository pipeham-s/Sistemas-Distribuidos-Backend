package com.example.backend_sistemas_distribuidos.business.controllers;

import com.example.backend_sistemas_distribuidos.business.entities.dto.ChatMessageDTO;
import com.example.backend_sistemas_distribuidos.business.entities.dto.ConversationDTO;
import com.example.backend_sistemas_distribuidos.business.entities.dto.ConversationRequestDTO;
import com.example.backend_sistemas_distribuidos.business.managers.ChatMessageMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private ChatMessageMgr chatMessageMgr;

    @MessageMapping("/send")
    public void handleMessage(ChatMessageDTO chatMessageDTO) {
        ChatMessageDTO savedMessageDTO = chatMessageMgr.sendMessage(
                chatMessageDTO.getSenderId(),
                chatMessageDTO.getReceiverId(),
                chatMessageDTO.getContent()
        );

        // Notificar a ambos usuarios sobre el nuevo mensaje
        chatMessageMgr.notifyUserMessages(chatMessageDTO.getReceiverId(), savedMessageDTO);
        chatMessageMgr.notifyUserMessages(chatMessageDTO.getSenderId(), savedMessageDTO);
    }

    @MessageMapping("/get-conversations")
    @SendToUser("/queue/conversations")
    public List<ConversationDTO> getConversations(Long userId) {
        return chatMessageMgr.getConversations(userId);
    }

    @GetMapping("/conversations/{conversationId}/messages")
    public List<ChatMessageDTO> getMessages(@PathVariable Long conversationId) {
        return chatMessageMgr.getMessagesByConversationId(conversationId);
    }

    @MessageMapping("/start-conversation")
    @SendToUser("/queue/conversations")
    public ConversationDTO startConversation(ConversationRequestDTO request) {
        return chatMessageMgr.createConversation(request.getInitiatorId(), request.getReceiverId());
    }
}

