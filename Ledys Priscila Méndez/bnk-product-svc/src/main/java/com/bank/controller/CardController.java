package com.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bank.pojo.output.detail.CardDetail;
import com.bank.process.info.IInfoProcess;


@RestController
public class CardController {

	private IInfoProcess<CardDetail> process;
	
	public CardController(IInfoProcess<CardDetail> process) {
		this.process = process;
	}
	@GetMapping("${config.endpoints.card}")
	public CardDetail getInfo(@PathVariable("usr") String user,
			@PathVariable("id") String id) {
		return process.retrieveBy(user, id);
	}
	@GetMapping("${config.endpoints.card-id}")
	public CardDetail getInfo(
			@PathVariable("id") String id) {
		return process.retrieveById(id);
	}
}
