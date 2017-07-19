package com.tr.springdemo.chat;

/**
 * Created by UC206612 on 7/19/2017.
 */
public class ChatMessage {
    MessageType type;
    String content;
    String from;
    String to;
    String id;
}

enum MessageType{
    Login,
    All,
    User
}
