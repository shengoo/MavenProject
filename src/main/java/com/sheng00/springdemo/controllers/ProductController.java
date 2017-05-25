package com.sheng00.springdemo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sheng00.springdemo.models.Customer;
import com.sheng00.springdemo.models.Product;
import com.sheng00.springdemo.repositories.CustomerRepository;
import com.sheng00.springdemo.repositories.ProductRepository;

@Controller
@RequestMapping("product")
public class ProductController {
	
	private final ProductRepository productRepository;
	private final CustomerRepository customerRepository;
	
	public ProductController(ProductRepository productRepository,CustomerRepository customerRepository){
		this.productRepository = productRepository;
		this.customerRepository = customerRepository;
	}
	
	@RequestMapping("")
	public String index(Model model){
		List<Product> products = productRepository.getAll();
		model.addAttribute("products", products);
		return "product/index";
	}
	
	@GetMapping("add")
	public String add(Product product){
		return "product/add";
	}
	
	@PostMapping("add")
	public String add(@Valid Product product, BindingResult bindingResult,
            RedirectAttributes redirectAttributes){
		if (bindingResult.hasErrors()) {
            return "product/add";
        }
		try {
			productRepository.addOne(product);
		} catch (Exception e) {
			// TODO: handle exception
			bindingResult.reject(e.getMessage());
            return "product/add";
		}
		redirectAttributes.addFlashAttribute("message",
                "Add Product Success.");
		return "redirect:/product";
	}
	
	@GetMapping("{id}")
	public String detail(@PathVariable String id,Model model){
		Product product = productRepository.getOne(id);
		List<Customer> customers = customerRepository.getByProduct(id); 
		model.addAttribute("product",product);
		model.addAttribute("customers",customers);
		return "product/detail";
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable String id){
		boolean result = productRepository.deleteOne(id);
		if(result){
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}else{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

}
