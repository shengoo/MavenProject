package com.tr.springdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MemberController {
	@RequestMapping("/member")
	public String Index() {
		return "NewFile";
	}
}
