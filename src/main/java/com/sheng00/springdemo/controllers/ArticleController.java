package com.sheng00.springdemo.controllers;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sheng00.springdemo.models.Article;
import com.sheng00.springdemo.storage.StorageService;

@Controller
@RequestMapping("article")
public class ArticleController {
	
	private final StorageService storageService;
	
	private Article[] articles = new Article[10];
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    public ArticleController(StorageService storageService) {
		for(int i = 0; i < 10; i++){
			Article a = new Article(counter.incrementAndGet(),"Hello." + i);
			articles[i] = a;
		}
        this.storageService = storageService;
    }
	
	@RequestMapping("")
	public String index(Model model){
		model.addAttribute("articles", articles);
		model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(ArticleController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));
		return "article/index";
	}
	
	@RequestMapping("detail")
	public Article detail(){
//		return "article/add";
		return new Article(counter.incrementAndGet(),"Hello."); 
	}
	
	@GetMapping("add")
	public String add(){
		return "article/add";
	}
	
	@PostMapping("add")
	public String add(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes){
		System.out.println(file.getSize());
		redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
		return "redirect:";
	}
	
	@RequestMapping("edit")
	public String edit(){
		return "article/edit";
	}
	
	@RequestMapping("delete")
	public String delete(){
		return "";
	}
	
}
