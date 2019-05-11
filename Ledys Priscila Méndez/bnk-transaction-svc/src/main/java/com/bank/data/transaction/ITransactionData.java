package com.bank.data.transaction;

import java.util.List;

import com.bank.pojo.output.TransactionDetail;
import com.bank.pojo.output.TransactionSave;

public interface ITransactionData {
	/**
	 * Retrieve transactions by product type, product id, user id, start date and end date
	 * @param prdId String Product id or account id
	 * @param usr String User id. Client
	 * @param start String Format: dd-MMM-yyyy Start date
	 * @param end String format: dd-MMM-yyyy End date
	 * @return
	 */
	public List<TransactionDetail> retrieveBy(String prdId, String usr, String start, String end);
	public TransactionSave save(TransactionSave save);
}
