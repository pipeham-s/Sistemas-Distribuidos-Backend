package com.example.backend_sistemas_distribuidos.business.managers;

import com.example.backend_sistemas_distribuidos.business.entities.ChatMessage;
import com.example.backend_sistemas_distribuidos.business.entities.Alumno;
import com.example.backend_sistemas_distribuidos.business.entities.Conversation;
import com.example.backend_sistemas_distribuidos.business.entities.dto.ChatMessageDTO;
import com.example.backend_sistemas_distribuidos.business.entities.dto.ConversationDTO;
import com.example.backend_sistemas_distribuidos.persistance.AlumnoRepository;
import com.example.backend_sistemas_distribuidos.persistance.ChatMessageRepository;
import com.example.backend_sistemas_distribuidos.persistance.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatMessageMgr {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public ChatMessageDTO sendMessage(Long senderId, Long receiverId, String content) {
        Alumno sender = alumnoRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("El remitente con ID " + senderId + " no existe."));
        Alumno receiver = alumnoRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("El destinatario con ID " + receiverId + " no existe."));

        Conversation conversation = conversationRepository.findByParticipantOneAndParticipantTwo(sender, receiver)
                .orElseGet(() -> {
                    Conversation newConversation = Conversation.builder()
                            .participantOne(sender)
                            .participantTwo(receiver)
                            .build();
                    return conversationRepository.save(newConversation);
                });

        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .content(content)
                .conversation(conversation)
                .build();

        ChatMessage savedMessage = chatMessageRepository.save(message);

        ChatMessageDTO messageDTO = toChatMessageDTO(savedMessage);

        messagingTemplate.convertAndSendToUser(receiverId.toString(), "/queue/messages", messageDTO);
        messagingTemplate.convertAndSendToUser(senderId.toString(), "/queue/messages", messageDTO);
        System.out.println("Enviando mensaje a " + receiverId + " y " + senderId + ": " + messageDTO);


        return messageDTO;
    }

    public List<ConversationDTO> getConversations(Long userId) {
        List<ChatMessage> messages = chatMessageRepository.findBySenderIdOrReceiverId(userId, userId);
        Map<Long, ConversationDTO> conversationsMap = new HashMap<>();

        for (ChatMessage message : messages) {
            Long contactId = message.getSender().getId().equals(userId) ? message.getReceiver().getId() : message.getSender().getId();
            String contactName = message.getSender().getId().equals(userId) ? message.getReceiver().getNombre() : message.getSender().getNombre();

            conversationsMap.putIfAbsent(contactId, new ConversationDTO(contactId, contactName, message.getContent()));
        }

        return new ArrayList<>(conversationsMap.values());
    }

    public List<ChatMessageDTO> getMessagesByConversationId(Long conversationId) {
        List<ChatMessage> messages = chatMessageRepository.findByConversationId(conversationId);
        return messages.stream().map(this::toChatMessageDTO).collect(Collectors.toList());
    }

    public void notifyUserMessages(Long userId, ChatMessageDTO message) {
        messagingTemplate.convertAndSendToUser(userId.toString(), "/queue/messages", message);
    }

    public ChatMessageDTO toChatMessageDTO(ChatMessage message) {
        return new ChatMessageDTO(
                message.getId(),
                message.getSender().getId(),
                message.getSender().getNombre(),
                message.getReceiver().getId(),
                message.getReceiver().getNombre(),
                message.getContent(),
                message.getConversation().getId()
        );
    }
    
}
