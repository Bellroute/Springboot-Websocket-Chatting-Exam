package com.example.websocket_chatting_exam.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {

    /*
    채팅방을 구현하기 위한 DTO
    */

    private String roomId;
    private String name;

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }

    // pub/sub 방식을 이용하면 구독자 관리가 알아서 되므로 웹소켓 세션 관리가 필요 없어짐.
    // 발송의 구현도 알아서 해결되므로 일일이 클라이언트에게 메시지를 발송하는 구현이 필요없어짐.
}