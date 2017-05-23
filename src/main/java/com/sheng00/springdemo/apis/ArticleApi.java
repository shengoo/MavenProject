package com.sheng00.springdemo.apis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/articles")
public class ArticleApi {
    
    
//	@RequestMapping(method="GET")
//	public Article detail(){
//		return new Article(counter.incrementAndGet(),"Hello."); 
//	}
}
