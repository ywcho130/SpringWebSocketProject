package com.cho.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
@ServerEndpoint(value="/cho")
public class WebSocketChat {
    
	// 웹소켓 세션의 목록을 저장하기 위한 ArrayList
	
    private static final List<Session> sessionList=new ArrayList<Session>();
    // 로그 메시지 출력 객체 
    private static final Logger logger = LoggerFactory.getLogger(WebSocketChat.class);
    public WebSocketChat() {
        // TODO Auto-generated constructor stub
        System.out.println("웹소켓(서버) 객체생성");
    }
    
    @OnOpen
    public void onOpen(Session session) {
        logger.info("Open session id:"+session.getId());
        try {
            session.getBasicRemote().sendText("대화방에 연결 되었습니다.");
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        sessionList.add(session);
    }
    
  
    // 나를 제외한 모든 세션에게 메시지를 전송하는 메서드
    private void sendMessageToAllSession(Session mine, String sender, String message) {
    	
        try {
        	// sessionList에 저장된 모든 세션에 대한 반복분
            for(Session session : WebSocketChat.sessionList) {
            	// 현재 순회 중인 세션이 'mine'세션과 동일하지 않은 경우 메시지 전송
                if(!mine.getId().equals(session.getId())) {
                    session.getBasicRemote().sendText(sender+" : "+message);
                }
            }
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    
   
    @OnMessage
    public void onMessage(String message,Session session) {
    	
    	/* 
    	 * 매개변수 message를 클라이언트가 보낸 messageinput과 클라이언트 id로 두개 나눠서 
    	 * 받아오면 되지 않을까라는 생각도 해봤지만 WebSocket 프로토콜은 메시지를 전달할 때
    	 *  하나의 문자열만을 지원하므로, 메시지를 여러개의 개별 매개변수로 나누는것은 올바르지 않다고 한다. 
    	 */
    	// message 문자열을 쉼표를 기준으로 분리하여 sender와 message변수에 할당한다.
    	String sender = message.split(",")[1];
    	message = message.split(",")[0];
    	
        logger.info("Message From "+sender + ": "+message);
        try {
        	// 내가 보낸 메시지를 표시하고
            session.getBasicRemote().sendText("<나> : "+message);
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        // 현재 세션을 제외한 모든 세션에게 메시지를 전송한다.
        sendMessageToAllSession(session, sender, message);
    }
    
    // 예외처리
    @OnError
    public void onError(Throwable e,Session session) throws IOException {
    	System.out.println("에러 발생 :" + e.getMessage());
    	session.close();
    }
    
    // 웹소켓 연결이 닫힐 때 호출되는 메서드
    @OnClose
    public void onClose(Session session) {
        logger.info("Session "+session.getId()+" has ended");
        // 종료된 세션을 sessionList에서 제거한다.
        sessionList.remove(session);
    }
}