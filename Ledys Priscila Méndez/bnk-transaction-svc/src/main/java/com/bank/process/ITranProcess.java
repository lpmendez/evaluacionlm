package com.bank.process;

import com.bank.pojo.input.ProcessReq;
import com.bank.pojo.output.Transaction;
import com.bank.pojo.output.TransactionSave;

public interface ITranProcess {
	public Transaction getTransByPrdAndAccAndDates(ProcessReq input);
	public TransactionSave save(TransactionSave input);
}
