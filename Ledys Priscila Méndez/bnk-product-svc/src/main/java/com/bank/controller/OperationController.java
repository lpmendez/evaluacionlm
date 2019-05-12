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

	//pago a tarjeta de credito propia
	@PostMapping("${config.endpoints.payment.own-card}")
	public Transaction ownPayCard(
			@RequestHeader("user") String user,
			@RequestBody OperationReq req) {
		req.setUser(user);
		return process.cardPayment(req);
	}

	//pago a prestamo propia
	@PostMapping("${config.endpoints.payment.own-loan}")
	public Transaction ownPayLoan(
			@RequestHeader("user") String user,
			@RequestBody OperationReq req) {
		req.setUser(user);
		return process.loanPayment(req);
	}
	
	//transferencia bancaria a terceros
	@PostMapping("${config.endpoints.transfer.third}")
	public Transaction thirdTransfer(
			@RequestHeader("user") String user,
			@RequestBody OperationReq req) {
		req.setUser(user);
		return process.thirdTransfer(req);
	}

	//pago a tarjeta de credito a terceros
	@PostMapping("${config.endpoints.payment.third-card}")
	public Transaction thirdPayCard(
			@RequestHeader("user") String user,
			@RequestBody OperationReq req) {
		req.setUser(user);
		return process.thirdCardPay(req);
	}

	//pago a prestamo a terceros
	@PostMapping("${config.endpoints.payment.third-loan}")
	public Transaction thirdLoanPay(
			@RequestHeader("user") String user,
			@RequestBody OperationReq req) {
		req.setUser(user);
		return process.thirdLoanPay(req);
	}
	
}
