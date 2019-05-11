package com.bank.data.product.acc;

import org.springframework.stereotype.Service;

import com.bank.data.product.IProductData;
import com.bank.pojo.product.AccountDetail;

@Service("AccDataStatic")
public class AccDataStatic implements IProductData<AccountDetail> {

	@Override
	public AccountDetail retrieveByUsrAndId(String usr, String id) {
		
		
		return null;
	}

}
