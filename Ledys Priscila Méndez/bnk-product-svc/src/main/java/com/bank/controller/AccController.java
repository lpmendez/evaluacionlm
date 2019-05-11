package com.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bank.pojo.output.detail.AccountDetail;
import com.bank.process.info.IInfoProcess;


@RestController
public class AccController {

	private IInfoProcess<AccountDetail> process;
	
	public AccController(IInfoProcess<AccountDetail> process) {
		this.process = process;
	}
	@GetMapping("${config.endpoints.account}")
	public AccountDetail getInfo(@PathVariable("usr") String user,
			@PathVariable("id") String id) {
		return process.retrieveBy(user, id);
	}

	@GetMapping("${config.endpoints.account-id}")
	public AccountDetail getInfo(
			@PathVariable("id") String id) {
		return process.retrieveById(id);
	}
}
