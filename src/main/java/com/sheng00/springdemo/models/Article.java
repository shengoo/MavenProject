package com.sheng00.springdemo.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Article {
//	private final long id;
//    private final String content;
    
    @NotNull
    @Size(min=2, max=10)
    private String title;
    
    @NotNull//(message="${validatedValue} can not be empty")
    @Size(min=2)
    private String content;

//    public Article(long id, String content) {
//        this.id = id;
//        this.content = content;
//    }

//    public long getId() {
//        return id;
//    }
//
//    public String getContent() {
//        return content;
//    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
