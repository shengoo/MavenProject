package com.sheng00.springdemo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.sheng00.springdemo.mappers.CustomerMapper;
import com.sheng00.springdemo.models.Customer;
import com.sheng00.springdemo.models.Product;
import com.sheng00.springdemo.repositories.CustomerRepository;
import com.sheng00.springdemo.repositories.ProductRepository;


@Controller
@RequestMapping("customer")
public class CustomerController {
	
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
	@Autowired
	private CustomerMapper customerMapper;
	
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
//			customerRepository.addOne(customer);
			customerMapper.addOne(customer);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			bindingResult.reject(e.getMessage());
            return "customer/add";
		}
		redirectAttributes.addFlashAttribute("message",
                "Add Customer Success.");
		return "redirect:/customer";
	}
	
	@GetMapping("{id}")
	public String detail(@PathVariable String id,Model model){
		Customer customer = customerRepository.getOne(id);
		model.addAttribute("customer",customer);
		List<Product> allProducts = productRepository.getAll();
		List<Product> hasProducts = productRepository.getByCustomer(id);
		model.addAttribute("products", hasProducts);
//		List<Product> availableProducts = 
				allProducts.removeAll(hasProducts);
				allProducts.removeIf((p)-> hasProducts.contains(p));
				System.out.println(allProducts.size());
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
	public String addProduct(@RequestParam String cid,@RequestParam(required=true) String pid,
			RedirectAttributes redirectAttributes){
		boolean result = customerRepository.addProduct(cid, pid);
		if(result){
			redirectAttributes.addFlashAttribute("message",
	                "Add Product Success.");
		}else {
			redirectAttributes.addFlashAttribute("error",
	                "Add Product Failed.");
		}
		return "redirect:" + cid;
	}
	
	@PostMapping("deleteProduct")
	public String deleteProduct(@RequestParam String cid,@RequestParam String pid,
			RedirectAttributes redirectAttributes){
		boolean result = customerRepository.deleteProduct(cid, pid);
		if(result){
			redirectAttributes.addFlashAttribute("message",
	                "Delete Product Success.");
		}else {
			redirectAttributes.addFlashAttribute("error",
	                "Delete Product Failed.");
		}
		return "redirect:" + cid;
	}
	

}
