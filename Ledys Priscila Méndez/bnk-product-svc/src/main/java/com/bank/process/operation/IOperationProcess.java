package com.bank.process.operation;

import com.bank.pojo.input.operations.OperationReq;
import com.bank.pojo.output.transaction.Transaction;

public interface IOperationProcess {
	/**
	 * 1. transferencia entre cuentas propias
	 * 2. pago de tarjeta de credito
	 * 3. pago de prestamo
	 * 4. transferencia hacia terceros
	 * 5. pago de tarjeta de credito a terceros
	 * 6. pago de prestamo a terceros
	 */
	public Transaction ownTransfer(OperationReq req);
	public Transaction cardPayment(OperationReq req);
	public Transaction loanPayment(OperationReq req);
	public Transaction thirdTransfer(OperationReq req);
	public Transaction thirdCardPay(OperationReq req);
	public Transaction thirdLoanPay(OperationReq req);
}
