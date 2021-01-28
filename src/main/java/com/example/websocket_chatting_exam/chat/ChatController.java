package com.example.websocket_chatting_exam.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    /*
    publisher 구현
    @MessageMapping을 통해 Websocket으로 들어오는 메시지 발행을 처리
    클라이언트에서는 /pub/chat/message 로 발행 요청을 하면 Controller가 해당 메시지를 받아 처리
    메시지가 발행되면 /sub/chat/room/{roomId} 로 메시지를 send 하는데, 클라이언트에서는 해당 주소를 구독(subscribe)하고 있다가 메시지가 전달되면 화면에 출력한다.

    깆존의 WebSockChatHandler가 했던 역할을 대체하므로 WebSockChatHandler는 삭제한다.
    */

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.JOIN.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
