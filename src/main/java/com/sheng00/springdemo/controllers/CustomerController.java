package com.sheng00.springdemo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sheng00.springdemo.models.Customer;
import com.sheng00.springdemo.repositories.CustomerRepository;


@Controller
@RequestMapping("customer")
public class CustomerController {
	
	private final CustomerRepository customerRepository;
	
	public CustomerController(CustomerRepository customerRepository){
		this.customerRepository = customerRepository;
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
		return "customer/detail";
	}

}
