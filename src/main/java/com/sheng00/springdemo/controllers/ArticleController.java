package com.sheng00.springdemo.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheng00.springdemo.models.Article;

@Controller
@RequestMapping("article")
public class ArticleController {
	
	private Article[] articles = new Article[10];
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("")
	public String index(Model model){
		model.addAttribute("articles", articles);
		return "article/index";
	}
	
	@RequestMapping("detail")
	public Article detail(){
//		return "article/add";
		return new Article(counter.incrementAndGet(),"Hello."); 
	}
	
	@RequestMapping("add")
	public String add(){
		return "article/add";
	}
	
	@RequestMapping("edit")
	public String edit(){
		return "article/edit";
	}
	
	@RequestMapping("delete")
	public String delete(){
		return "";
	}
	
	public ArticleController(){
		for(int i = 0; i < 10; i++){
			Article a = new Article(counter.incrementAndGet(),"Hello." + i);
			articles[i] = a;
		}
	}
}
