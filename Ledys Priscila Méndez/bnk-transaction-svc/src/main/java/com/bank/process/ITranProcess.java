package com.bank.process;

import com.bank.pojo.input.ProcessReq;
import com.bank.pojo.output.Transaction;

public interface ITranProcess {
	public Transaction getTransByPrdAndAccAndDates(ProcessReq input);
}
