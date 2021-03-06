package com.tr.springdemo.models;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Product {
	public Product(){
		
	}
	public Product(UUID id,String name){
		this.id = id;
		this.name = name;
	}
	
	private UUID id;
	
    @NotNull
    @Size(min=2, max=20)
	private String name;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Product))return false;
	    Product otherProduct = (Product)other;
	    return this.id.equals(otherProduct.id);
	}
}
