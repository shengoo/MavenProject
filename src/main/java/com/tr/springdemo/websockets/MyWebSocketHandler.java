package com.tr.springdemo.websockets;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.tr.springdemo.Application;
import com.tr.springdemo.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;

public class MyWebSocketHandler implements WebSocketHandler {
	private static final Logger logger;
	private int count = 0;
	


    private static final ArrayList<WebSocketSession> users;

    static {
        users = new ArrayList<>();
        logger = LoggerFactory.getLogger(MyWebSocketHandler.class);
        (new Thread(new Runner())).start();
    }
    
    private MyWebSocketHandler() {
		
	}
    
    private static MyWebSocketHandler instance = null;
    public static MyWebSocketHandler getInstance(){
    	if(instance == null){
    		instance = new MyWebSocketHandler();
    	}
    	return instance;
    }
    
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("connect to the websocket success......");
        users.add(session);
        session.sendMessage(new TextMessage("Welcome, " + session.getAttributes().get(Constants.WEBSOCKET_USERNAME)));
//        String userName = (String) session.getAttributes().get(Constants.WEBSOCKET_USERNAME);
//        if(userName!= null){
//            //查询未读消息
//            int count = webSocketService.getUnReadNews((String) session.getAttributes().get(Constants.WEBSOCKET_USERNAME));
//
//            session.sendMessage(new TextMessage(count + ""));
//        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        logger.debug("handleMessage");
        
        sendMessageToUsers(new TextMessage("Hi"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        logger.debug("websocket connection closed......");
        users.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        users.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        logger.debug("sendMessageToUsers");
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        logger.debug("sendMessageToUser");
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(Constants.WEBSOCKET_USERNAME).equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    
}

class Runner implements Runnable{
	
	private int count = 0;
	private MyWebSocketHandler handler = MyWebSocketHandler.getInstance();
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			handler.sendMessageToUsers(new TextMessage("Server started : " + count++));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
