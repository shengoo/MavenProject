package com.tr.springdemo.apis;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.springdemo.storage.StorageService;

@RestController
@RequestMapping("api/files")
public class FileApi {

	private final StorageService storageService;

    @Autowired
	public FileApi(StorageService storageService){
		this.storageService = storageService;
	}
	
	@RequestMapping("")
	public java.util.List<String> List(){
		return  storageService
                .loadAll().map(path -> path.toString())
                .collect(Collectors.toList());
	}
}
