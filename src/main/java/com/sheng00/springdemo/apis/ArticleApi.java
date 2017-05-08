package com.sheng00.springdemo.apis;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheng00.springdemo.models.Article;

@RestController
@RequestMapping("api/articles")
public class ArticleApi {

	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    
//	@RequestMapping(method="GET")
//	public Article detail(){
//		return new Article(counter.incrementAndGet(),"Hello."); 
//	}
}
