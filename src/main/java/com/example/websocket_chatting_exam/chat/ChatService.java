package com.example.websocket_chatting_exam.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    /*
    채팅방을 생성, 조회하고 하나의 세션에 메시지 발송을 하는 서비스
    */

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms; // 서버에 생성된 모든 채팅방의 정보를 모아둔 구조체 == DB

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    // 채팅방 리스트 조회
    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    // roomId로 채팅방 조회
    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    // 채팅방 생성
    public ChatRoom createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                                    .roomId(randomId)
                                    .name(name)
                                    .build();
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }

    // 지정한 Websocket 세션에 메시지 발송
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
