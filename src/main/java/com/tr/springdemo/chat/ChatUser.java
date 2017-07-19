package com.tr.springdemo.chat;

import org.springframework.web.socket.WebSocketSession;

/**
 * Created by UC206612 on 7/19/2017.
 */
public class ChatUser {
    String id;
    String name;
    WebSocketSession session;
}
