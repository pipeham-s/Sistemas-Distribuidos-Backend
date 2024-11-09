package com.example.backend_sistemas_distribuidos.persistance;

import com.example.backend_sistemas_distribuidos.business.entities.ChatMessage;
import com.example.backend_sistemas_distribuidos.business.entities.Conversation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {


    List<ChatMessage> findBySenderIdOrReceiverId(Long senderId, Long receiverId);

    Optional<ChatMessage> findTopByConversationOrderByIdDesc(Conversation conversation);

    List<ChatMessage> findByConversationId(Long conversationId);




}
