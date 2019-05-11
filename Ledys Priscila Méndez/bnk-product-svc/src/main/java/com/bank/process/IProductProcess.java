package com.bank.process;

import com.bank.pojo.output.all.ProductRes;


public interface IProductProcess {
	public ProductRes getAllProducts(String user);
}
