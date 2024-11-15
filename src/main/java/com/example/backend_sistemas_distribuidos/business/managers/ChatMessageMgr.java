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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        Conversation conversation = conversationRepository.findByParticipants(sender, receiver)
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

        // Notificar al receptor y al remitente sobre el nuevo mensaje
        messagingTemplate.convertAndSendToUser(receiverId.toString(), "/queue/messages", messageDTO);
        messagingTemplate.convertAndSendToUser(senderId.toString(), "/queue/messages", messageDTO);
        System.out.println("Enviando mensaje a " + receiverId + " y " + senderId + ": " + messageDTO);

        return messageDTO;
    }

    @Transactional(readOnly = true)
    public List<ConversationDTO> getConversations(Long userId) {
        List<Conversation> conversations = conversationRepository.findByParticipantOneIdOrParticipantTwoId(userId, userId);

        return conversations.stream()
                .map(conversation -> {
                    Long contactId = conversation.getParticipantOne().getId().equals(userId)
                            ? conversation.getParticipantTwo().getId()
                            : conversation.getParticipantOne().getId();
                    String contactName = conversation.getParticipantOne().getId().equals(userId)
                            ? conversation.getParticipantTwo().getNombre()
                            : conversation.getParticipantOne().getNombre();
                    String lastMessage = conversation.getMessages() != null && !conversation.getMessages().isEmpty()
                            ? conversation.getMessages().get(conversation.getMessages().size() - 1).getContent()
                            : null;

                    return new ConversationDTO(
                            conversation.getId(),  // Nuevo campo agregado para conversationId
                            contactId,
                            contactName,
                            lastMessage
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChatMessageDTO> getMessagesByConversationId(Long conversationId) {
        List<ChatMessage> messages = chatMessageRepository.findByConversationId(conversationId);
        System.out.println("Mensajes obtenidos para la conversación ID " + conversationId + ": " + messages);
        return messages.stream().map(this::toChatMessageDTO).collect(Collectors.toList());
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

    public ConversationDTO createConversation(Long initiatorId, Long receiverId) {
        Alumno initiator = alumnoRepository.findById(initiatorId)
                .orElseThrow(() -> new IllegalArgumentException("El usuario con ID " + initiatorId + " no existe."));
        Alumno receiver = alumnoRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("El usuario con ID " + receiverId + " no existe."));

        // Verificar si ya existe una conversación entre los usuarios
        Conversation conversation = conversationRepository.findByParticipants(initiator, receiver)
                .orElseGet(() -> {
                    Conversation newConversation = Conversation.builder()
                            .participantOne(initiator)
                            .participantTwo(receiver)
                            .build();
                    return conversationRepository.save(newConversation);
                });

        // Retornar la conversación creada o existente en formato DTO
        return new ConversationDTO(
                conversation.getId(),  // Nuevo campo agregado para conversationId
                receiver.getId(),
                receiver.getNombre(),
                null
        );
    }
    public void notifyUserMessages(Long userId, ChatMessageDTO message) {
        messagingTemplate.convertAndSendToUser(userId.toString(), "/queue/messages", message);
    }
}



