package com.sheng00.springdemo.models;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;



public interface CustomerMapper {
	@Select("SELECT * from Customer WHERE id = #{id}")
	Customer getCustomer(@Param("id") String id);
}
