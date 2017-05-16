package com.sheng00.springdemo.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
