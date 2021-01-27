package com.example.websocket_chatting_exam.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    /*
    채팅 메시지를 주고받기 위한 DTO
    */

    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 채팅방 번호
    private String sender; // 메시지 보낸 사람
    private String message; // 메시지
}
