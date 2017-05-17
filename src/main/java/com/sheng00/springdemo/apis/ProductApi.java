package com.sheng00.springdemo.apis;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheng00.springdemo.models.Product;
import com.sheng00.springdemo.repositories.ProductRepository;

@RestController
@RequestMapping("api/products")
public class ProductApi {
	
	
	private final ProductRepository productRepository;
	
	public ProductApi(ProductRepository productRepository){
		this.productRepository = productRepository;
	}
	
	@GetMapping("")
	public List<Product> list() {
		return productRepository.getAll();
	}
	
	
}
