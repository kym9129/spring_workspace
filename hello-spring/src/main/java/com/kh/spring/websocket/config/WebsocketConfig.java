package com.kh.spring.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer{
	
	@Autowired
	WebsocketHandler websocketHandler; //방금 만든 핸들러

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// /websoooocket이라고 요청했을 때 websocketHandler를 추가한다
		// client의 const ws = new WebSocket("ws://localhost:9090/spring/websoooocket");	에 대응
		registry
			.addHandler(websocketHandler, "/websoooocket")
			.withSockJS(); //Server side에서도 sockjs 선언
	}

}
