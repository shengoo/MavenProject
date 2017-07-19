package com.tr.springdemo.chat;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;

public class ChatHandler implements WebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(ChatHandler.class);

    private static final ArrayList<ChatUser> users = new ArrayList<>();
    Gson gson = new Gson();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("afterConnectionEstablished : " + session);
        ChatMessage message = new ChatMessage();
        message.to = "setid";
        message.content = session.getId();
        session.sendMessage(new TextMessage(gson.toJson(message)));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("handleMessage : " + message.getPayload());

        ChatMessage message1 = gson.fromJson((String)message.getPayload(),ChatMessage.class);
        switch (message1.type){
            case All:
                // send to all
                break;
            case User:
                // send to someone
                break;
            case Login:
                //login
                joined(session,message1);
                break;
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("afterConnectionEstablished : " + session);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("afterConnectionClosed : " + session);

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    public void joined(WebSocketSession session,ChatMessage message){
        ChatUser user = new ChatUser();
        user.id = session.getId();
        user.name = message.content;
        user.session = session;
        users.add(user);
        ChatMessage message1 = new ChatMessage();
        message1.type = MessageType.All;
        message1.content = "Welcome " + user.name;
        message1.from = "system";
        message1.id = user.id;
        sendMessageToUsers(message1);
    }

    public void left(ChatUser user){

    }


    public void sendMessageToUsers(ChatMessage message){
        System.out.println("sendMessageToUsers : " + gson.toJson(message));
        for (ChatUser user : users) {
            try {
                if (user.session.isOpen()) {
                    user.session.sendMessage(new TextMessage(gson.toJson(message)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToUser(){

    }

}
