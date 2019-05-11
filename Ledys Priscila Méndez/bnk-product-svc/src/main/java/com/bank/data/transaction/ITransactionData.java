package com.bank.data.transaction;

import com.bank.pojo.output.transaction.Transaction;

public interface ITransactionData {
	public Transaction save(Transaction input);
}
