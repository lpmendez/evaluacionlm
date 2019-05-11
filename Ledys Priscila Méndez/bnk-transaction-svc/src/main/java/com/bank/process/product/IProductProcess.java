package com.bank.process.product;

import com.bank.pojo.input.ProcessReq;
import com.bank.pojo.output.Transaction;

public interface IProductProcess {
	public Transaction execute(ProcessReq input);
}
