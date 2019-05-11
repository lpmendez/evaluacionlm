package com.bank.data;

import java.util.List;

public interface IProductData<T> {
	public List<T> retrieveAllByUser(String user);
	public T retrieveByUsrAndId(String user, String id);
	public T retrieveById(String id);
}
