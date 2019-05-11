package com.bank.data;

import java.util.List;

public interface IProductData<T> {
	public List<T> retrieveAllByUser(String user);
	public T retrieveByUsrAndId(String user, String id);
	public T retrieveById(String id);
	
	/**
	 * Operation to process
	 * @param prdId: String. product id or account id
	 * @param amount: double. Amount to process
	 * @return
	 */
	public boolean operation(String prdId, double amount);
}
