package com.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bank.pojo.output.all.ProductRes;
import com.bank.process.IProductProcess;


@RestController
public class ProductController {

	private IProductProcess process;
	
	public ProductController(IProductProcess process) {
		this.process = process;
	}
	@GetMapping("${config.endpoints.accounts}")
	public ProductRes getAccounts(@RequestHeader("user") String user) {
		return process.getAllProducts(user);
	}
}
