package com.farmers.oliview.chatting.model.websocket;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.farmers.oliview.chatting.model.dto.Message;
import com.farmers.oliview.chatting.model.service.ChattingService;
import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.together.dto.Together;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChattingWebsocketHandler extends TextWebSocketHandler{
    
	// WebSocketSession : 클라이언트 - 서버간 전이중통신을 담당하는 객체 (JDBC Connection과 유사)
	// 					  클라이언트 세션 다룰 수 있음
	// sessions : 클라이언트 세션을 모아둔 set // 모든 사용자, 원하는 사용자 찾기 가능
	private Set<WebSocketSession> sessions  = Collections.synchronizedSet(new HashSet<WebSocketSession>());

	private final ChattingService service;
    
    
    // afterConnectionEstablished - 클라이언트와 연결이 완료되고, 통신할 준비가 되면 실행. 
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    	//매개변수 session == 한 명의 클라이언트
        sessions.add(session);
        
    }
    
    
    //handlerTextMessage - 클라이언트로부터 텍스트 메세지를 받았을때 실행
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        
        // 전달받은 내용은 JSON 형태의 String
    	// payLoad : 적재된 것.전달받은 데이터 
        log.info("전달받은 내용 : " + message.getPayload());
        
        // Jackson에서 제공하는 객체
        // JSON String -> DTO Object
        // 전달받은 내용 : {"senderNo":"4", "chattingNo":"8","messageContent":"실시간"}
        ObjectMapper objectMapper = new ObjectMapper();
        
        Message msg = objectMapper.readValue( message.getPayload(), Message.class);
        // Message 객체 확인
        System.out.println(msg); 
        
        // 메세지 DB 삽입 서비스 호출
        int result = service.insertMessage(msg);
        
        if(result > 0 ) {
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm");
            msg.setSendTime(sdf.format(new Date()) );
            
            log.info("세션 수 : " + sessions.size());
            
            // 전역변수로 선언된 sessions에는 접속중인 모든 회원의 세션 정보가 담겨 있음
            for(WebSocketSession s : sessions) { 
            	
                // WebSocketSession은 HttpSession의 속성을 가로채서 똑같이 가지고 있기 때문에
                // 회원 정보를 나타내는 loginMember도 가지고 있음. // together도 갖고있음.
                
                // 로그인된 회원 정보 중 회원 번호 얻어오기 // session은 처음엔 이름밖에 모르기에 중간에 가로채서 보다 많은 정보를 얻어옴
            	HttpSession temp = (HttpSession)s.getAttributes().get("session");
            	
            	// 꺼내온 세션에서 채팅번호 얻어오기. 이 채팅방에만 채팅을 보내겠다 // 이게 굳이 필요함???
                int chattingNo = ((Together)temp.getAttribute("together")).getBoardNo();
                log.debug("chattingNo : " + chattingNo);
                
                // 로그인 상태인 회원 중 chattingNo가 일치하는 회원에게 메세지 전달 // ???? 이게 맞나,,,ㅋㅎ,,,,
                if(chattingNo == msg.getChattingNo()) {
                    s.sendMessage(new TextMessage(new Gson().toJson(msg)));
                }
            }
        }
    }
    
    
    
    // afterConnectionClosed - 클라이언트와 연결이 종료되면 실행된다.
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session); // 나간 클라이언트 set에서 제거(명단에서 제외)
    }
    
}