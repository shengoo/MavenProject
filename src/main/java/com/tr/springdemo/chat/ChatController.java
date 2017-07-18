package com.tr.springdemo.chat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("chat")
public class ChatController {
	@RequestMapping("")
	public String index(Model model){
		return "chat/index";
	}
}
