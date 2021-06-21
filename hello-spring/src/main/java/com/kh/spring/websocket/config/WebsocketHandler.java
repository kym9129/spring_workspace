package com.kh.spring.websocket.config;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Component //Bean으로 등록할 때 사용하는 어노테이션
@Slf4j
public class WebsocketHandler extends TextWebSocketHandler {
	
	/**
	 * multi-thread에서 동기화를 지원하는 list
	 */
	List<WebSocketSession> sessionList = new CopyOnWriteArrayList<>();
	
	/**
	 * websocke연결 셩공 후 호출 = onopen
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionList.add(session);
		log.debug("onopen({}명) : {}",sessionList.size(), session);
	}

	/**
	 * client가 message를 전송한 경우 호출 = onmessage
	 * 지금은 textMessage만 쓸거라서 hadleTextMessage선택
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.debug("onmessage : {} from {}", message, session);
		
		String sender = session.getId(); //보낸사람
		TextMessage msg = new TextMessage(sender + " : " + message.getPayload()); //메세지 내용
		
		for(WebSocketSession sess : sessionList) {
			sess.sendMessage(msg); //각각의 세션에 메세지를 전달
		}
	}

	/**
	 * websocket 연결 해제 후 호출 = onclose
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessionList.remove(session);
		log.debug("onclose({}명) : {}", sessionList.size(), session);
	}
	
	

}
