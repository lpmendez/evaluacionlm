package com.bank.data;

import java.util.List;

public interface IProductData<T> {
	public List<T> retrieveAllByUser(String user);
}
