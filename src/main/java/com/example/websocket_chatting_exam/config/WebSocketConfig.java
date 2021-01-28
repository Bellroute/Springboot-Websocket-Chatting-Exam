package com.example.websocket_chatting_exam.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /*
    WebSocket을 활성화하기 위한 Config 파일
    @EnableWebSocket을 선언하여 WebSocket을 활성화함
    */

    /*
    Stomp를 사용하기 위해 @EnableWebSocketMessageBroker를 선언하고
    WebSocketMessageBrokerConfigurer를 상속받아 configureMessageBroker를 구현한다.
    */

    private final WebSocketHandler webSocketHandler;

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        // WebSocket에 접속하기 위한 엔드포인트를 /ws/chat으로 설정
//        // 다른 서버에서도 접속 가능하도록 CORS : setAllowedOrigins("*")룰 설정
//        // 클라이언트에서 ws://localhost:8080/ws/chat으로 커넥션을 연결하게 되면 통신이 가능하게 됨.
//        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
//    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // pub/sub 메시징을 구현하기 위해
        // 메시지를 발행하는 요청의 prefix는 /pub로 시작하도록 설정
        // 메시지를 구독하는 요청의 prefix는 /sub로 시작하도록 설정
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp websocket의 연결 endpoint는 /ws-stomp로 설정
        // ws://{domain}/ws-stomp
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
