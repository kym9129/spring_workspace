package com.kh.spring.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {

	/**
	 * client에서 최초 websocket 접속 url을 설정
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stommmp").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//1. SimpleBroker방식
		registry.enableSimpleBroker("/notice");
		
		//2. MessageHandler방식 : /app/a --> @MessageMapping("/a")
		//(Websocket계의 @RequestMapping느낌)
		//관리자만 공지를 보낼 수 있다는 가정 하에 /admin 이라고 지정했음 (아무거나 해도 됨)
		registry.setApplicationDestinationPrefixes("/admin");
	}

	
}
