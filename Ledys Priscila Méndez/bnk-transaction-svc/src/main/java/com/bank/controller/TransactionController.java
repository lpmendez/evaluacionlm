package com.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.pojo.input.ProcessReq;
import com.bank.pojo.output.Transaction;
import com.bank.process.ITranProcess;

@RestController
public class TransactionController {

	private ITranProcess process;
	
	public TransactionController (ITranProcess process) {
		this.process = process;
	}
	
	@GetMapping("${config.endpoints.transactions}")
	public Transaction getTransByAccAndProd(
			@RequestHeader("user") String user,
			@PathVariable("accountID") String account,
			@RequestParam("start") String start,
			@RequestParam("end") String end,
			@RequestParam("prd") String prd) {
		
		ProcessReq input = new ProcessReq();
		input.setAccount(account);
		input.setStartDate(start);
		input.setEndDate(end);
		input.setType(prd);
		input.setUser(user);
		
		return process.getTransByPrdAndAccAndDates(input);
	}
}
