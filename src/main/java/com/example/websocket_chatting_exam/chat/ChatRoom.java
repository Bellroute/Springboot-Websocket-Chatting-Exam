package com.example.websocket_chatting_exam.chat;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {

    /*
    채팅방을 구현하기 위한 DTO
    */

    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>(); // 입장한 클라이언트들의 정보

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    // 입장, 대화하기의 기능을 분기하기위한 메소드
    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            // 채팅 입장 시 클라이언트 session을 리스트에 추가해둠
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessage, chatService);
    }

    // 채팅룸의 모든 session에게 메시지를 발송
    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}