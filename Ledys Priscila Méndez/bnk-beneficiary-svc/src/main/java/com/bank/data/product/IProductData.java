package com.bank.data.product;

public interface IProductData<T> {
	public T retrieveByUsrAndId(String usr, String id);
	public T retrieveById(String id);
}
