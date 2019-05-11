package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bank.pojo.input.operations.OperationReq;
import com.bank.pojo.output.transaction.Transaction;
import com.bank.process.operation.IOperationProcess;

@RestController
public class OperationController {

	@Autowired
	private IOperationProcess process;

	//transferencia bancaria entre cuentas propias
	@PostMapping("${config.endpoints.transfer.own}")
	public Transaction ownTransfer(
			@RequestHeader("user") String user,
			@RequestBody OperationReq req) {
		req.setUser(user);
		return process.ownTransfer(req);
	}
}
