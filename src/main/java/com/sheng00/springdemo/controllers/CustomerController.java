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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sheng00.springdemo.models.Customer;
import com.sheng00.springdemo.models.Product;
import com.sheng00.springdemo.repositories.CustomerRepository;
import com.sheng00.springdemo.repositories.ProductRepository;


@Controller
@RequestMapping("customer")
public class CustomerController {
	
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
	
	public CustomerController(CustomerRepository customerRepository,ProductRepository productRepository){
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
	}
	
	@RequestMapping("")
	public String index(Model model){
		List<Customer> customers = customerRepository.getAll();
		model.addAttribute("customers", customers);
		return "customer/index";
	}
	
	@GetMapping("add")
	public String add(Customer customer){
		return "customer/add";
	}
	
	@PostMapping("add")
	public String add(@Valid Customer customer, BindingResult bindingResult,
            RedirectAttributes redirectAttributes){
		if (bindingResult.hasErrors()) {
            return "customer/add";
        }
		try {
			customerRepository.addOne(customer);
		} catch (Exception e) {
			// TODO: handle exception
			bindingResult.reject(e.getMessage());
            return "customer/add";
		}
		redirectAttributes.addFlashAttribute("message",
                "Success.");
		return "redirect:";
	}
	
	@GetMapping("{id}")
	public String detail(@PathVariable String id,Model model){
		Customer customer = customerRepository.getOne(id);
		model.addAttribute("customer",customer);
		List<Product> allProducts = productRepository.getAll();
//		List<Product> hasProduct = 
		model.addAttribute("availableProducts", allProducts);
		return "customer/detail";
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable String id){
		boolean result = customerRepository.deleteOne(id);
		if(result){
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}else{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	@PostMapping("addproduct")
	public String addProduct(@RequestParam String cid){
		return "redirect:" + cid;
	}
	
	

}
