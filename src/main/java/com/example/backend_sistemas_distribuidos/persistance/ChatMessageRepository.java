package com.example.backend_sistemas_distribuidos.persistance;

import com.example.backend_sistemas_distribuidos.business.entities.ChatMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {

    // Método para encontrar un mensaje por su ID
    Optional<ChatMessage> findOneById(Long id);

    // Método para encontrar mensajes enviados por un remitente específico
    List<ChatMessage> findAllBySenderId(Long senderId);

    // Método para encontrar mensajes recibidos por un destinatario específico
    List<ChatMessage> findAllByReceiverId(Long receiverId);

    // Método para encontrar mensajes entre dos usuarios específicos
    List<ChatMessage> findAllBySenderIdAndReceiverId(Long senderId, Long receiverId);

    // Método para verificar si existe un mensaje con un ID específico
    boolean existsById(Long id);

    // Método para obtener todos los mensajes
    Iterable<ChatMessage> findAll();
}
