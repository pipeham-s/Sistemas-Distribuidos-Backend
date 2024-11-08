package com.example.backend_sistemas_distribuidos.business.managers;

import com.example.backend_sistemas_distribuidos.business.entities.ChatMessage;
import com.example.backend_sistemas_distribuidos.business.entities.Alumno;
import com.example.backend_sistemas_distribuidos.business.entities.ConversationDTO;
import com.example.backend_sistemas_distribuidos.persistance.AlumnoRepository;
import com.example.backend_sistemas_distribuidos.persistance.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageMgr {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public ChatMessage sendMessage(Long senderId, Long receiverId, String content) {
        Alumno sender = alumnoRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("El remitente con ID " + senderId + " no existe."));
        Alumno receiver = alumnoRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("El destinatario con ID " + receiverId + " no existe."));

        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .content(content)
                .build();

        ChatMessage savedMessage = chatMessageRepository.save(message);

        // Notificar al destinatario a través de STOMP
        messagingTemplate.convertAndSend("/topic/chat/" + receiverId, savedMessage);

        return savedMessage;
    }
    public List<ConversationDTO> getConversations(Long userId) {
        List<ChatMessage> messages = chatMessageRepository.findBySenderIdOrReceiverId(userId, userId);

        Map<Long, ConversationDTO> conversationsMap = new HashMap<>();

        for (ChatMessage message : messages) {
            Long contactId = message.getSender().getId().equals(userId) ? message.getReceiver().getId() : message.getSender().getId();
            String contactName = message.getSender().getId().equals(userId) ? message.getReceiver().getNombre() : message.getSender().getNombre();

            if (!conversationsMap.containsKey(contactId)) {
                conversationsMap.put(contactId, new ConversationDTO(
                        contactId,
                        contactName,
                        message.getContent(),
                        message.getTimestamp()
                ));
            } else {
                if (message.getTimestamp().isAfter(conversationsMap.get(contactId).getLastMessageTime())) {
                    conversationsMap.get(contactId).setLastMessage(message.getContent());
                    conversationsMap.get(contactId).setLastMessageTime(message.getTimestamp());
                }
            }
        }

        return new ArrayList<>(conversationsMap.values());
    }

}


