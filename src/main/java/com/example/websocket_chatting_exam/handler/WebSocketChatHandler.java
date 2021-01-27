package com.example.websocket_chatting_exam.handler;

import com.example.websocket_chatting_exam.chat.ChatMessage;
import com.example.websocket_chatting_exam.chat.ChatRoom;
import com.example.websocket_chatting_exam.chat.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    /*
    socket통신은 서버와 클라이언트가 1:N으로 관계를 맺는다.
    한 서버에 여러 클라이언트가 접속 가능, 서버는 여러 클라이언트가 발송한 메시지를 받아 처래해 줄 handler가 필요함.
    */

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Client로부터 받은 메시지를 Console log에 출력하고 Client로 환영 메시지를 보냄
        String payload = message.getPayload();
        log.info("payload: {}", payload);
// 삭제        TextMessage textMessage = new TextMessage("Welcome chatting server~^^");
// 삭제       session.sendMessage(textMessage);

        // websocket 클라이언트로부터 채팅 메시지를 전달받아 채팅 메시지 객체로 변환
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        // 전달받은 메시지에 담긴 채팅방 id로 발송 대상 채팅방 정보를 조회함
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        // 해당 채팅방에 입장해있는 모든 클라이언트들에게 메시지 발송
        room.handleActions(session, chatMessage, chatService);
    }
}
