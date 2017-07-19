package com.tr.springdemo.websockets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
//@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig2 extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
    	registry.addHandler(MyWebSocketHandler.getInstance(), "/webSocketServer")
    	.addInterceptors(new WebSocketHandshakeInterceptor());
    }
    
//    @Bean
//    public WebSocketHandler systemWebSocketHandler(){
//    	return new MyWebSocketHandler();
//    }
}
