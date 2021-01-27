package com.example.websocket_chatting_exam.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    /*
    WebSocket을 활성화하기 위한 Config 파일
    @EnableWebSocket을 선언하여 WebSocket을 활성화함
    */

    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // WebSocket에 접속하기 위한 엔드포인트를 /ws/chat으로 설정
        // 다른 서버에서도 접속 가능하도록 CORS : setAllowedOrigins("*")룰 설정
        // 클라이언트에서 ws://localhost:8080/ws/chat으로 커넥션을 연결하게 되면 통신이 가능하게 됨.
        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
    }
}
