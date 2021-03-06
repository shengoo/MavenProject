package com.tr.springdemo.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import com.tr.springdemo.models.Customer;



public interface CustomerMapper {
	@Select("SELECT * from Customer WHERE id = #{id}")
	Customer getCustomer(@Param("id") String id);
	
	@Insert("INSERT INTO Customer (name) VALUES (#{name})")
	void addOne(Customer customer);
}
