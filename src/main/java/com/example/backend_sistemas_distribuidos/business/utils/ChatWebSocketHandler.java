package com.example.backend_sistemas_distribuidos.business.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Suponiendo que el mensaje recibido es un JSON con el contenido, remitente y destinatario
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());

        String content = jsonNode.get("content").asText();
        String senderId = jsonNode.get("senderId").asText();
        String receiverId = jsonNode.get("receiverId").asText();

        // Enviar el mensaje solo a la sesión del destinatario
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen() && webSocketSession.getId().equals(receiverId)) {
                webSocketSession.sendMessage(new TextMessage(content));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}


