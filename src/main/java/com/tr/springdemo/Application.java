package com.tr.springdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.socket.TextMessage;

import com.tr.springdemo.websockets.MyWebSocketHandler;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class Application extends SpringBootServletInitializer {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("run");
        (new Thread(new Runner())).start();
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