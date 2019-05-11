package com.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bank.pojo.output.detail.LoanDetail;
import com.bank.process.info.IInfoProcess;


@RestController
public class LoanController {

	private IInfoProcess<LoanDetail> process;
	
	public LoanController(IInfoProcess<LoanDetail> process) {
		this.process = process;
	}
	@GetMapping("${config.endpoints.loan}")
	public LoanDetail getInfo(@PathVariable("usr") String user,
			@PathVariable("id") String id) {
		return process.retrieveBy(user, id);
	}
}
