package com.sheng00.springdemo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sheng00.springdemo.models.Article;
//import com.sheng00.springdemo.repositories.ArticleRepository;
import com.sheng00.springdemo.storage.StorageService;

@Controller
@RequestMapping("article")
public class ArticleController {
	
	private final StorageService storageService;
//	private final ArticleRepository articleRepository;
	
	private Article[] articles = new Article[10];
	@Autowired
    public ArticleController(StorageService storageService) {
		for(int i = 0; i < 10; i++){
//			Article a = new Article(counter.incrementAndGet(),"Hello." + i);
			Article a = new Article();
			articles[i] = a;
		}
        this.storageService = storageService;
//        this.articleRepository = articleRepository;
    }
	
	@RequestMapping("")
	public String index(Model model){
		model.addAttribute("articles", articles);
		return "article/index";
	}
	
	@GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }
	
	@RequestMapping("detail")
	public String detail(){
		return "article/add";
//		return new Article(counter.incrementAndGet(),"Hello."); 
	}
	
	@GetMapping("add")
	public String add(Article article){
		return "article/add";
	}
	
	@PostMapping("add")
	public String add(@Valid Article article, BindingResult bindingResult,
            RedirectAttributes redirectAttributes){
		if (bindingResult.hasErrors()) {
            return "article/add";
        }
		redirectAttributes.addFlashAttribute("message",
                "Success.");
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
