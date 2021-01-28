package com.example.websocket_chatting_exam.chat;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {

    /*
    채팅방을 생성하고 정보를 조회하는 Repository.
    실습에서는 간단하게 만드는 것이기 때문에 Map으로 관리하지만, 서비스에서는 DB나 다른 저장 매체를 사용해야함.
    ChatService 기능을 대신 수행하기 때문에 ChatService는 필요 없음
    */

    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        // 채팅방 생성순서 최근 순으로 변환
        List<ChatRoom> chatRooms = new ArrayList(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatRoom findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}
